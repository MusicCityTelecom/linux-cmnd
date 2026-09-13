using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Drawing;
using System.Globalization;
using System.IO;
using System.Linq;
using System.Media;
using System.Reflection;
using System.Text;
using System.Threading;
using System.Windows.Forms;
using Philips.PSG.Gateway.Data;

namespace Philips.PSG.Gateway;

public class MainForm : Form
{
	private delegate void Invoke_UpdateControls();

	private delegate void Invoke_UpdateDekTecDeviceStatusDisplay();

	private DekTecManager _dekTecManager;

	private Catalog _catalog;

	private BuildProgressDialog _buildProgressDialog;

	private ConfigurationDialog _configDialog;

	private StreamWriter _buildLog;

	private TsPlayer _tsPlayer;

	private IContainer components;

	private MenuStrip menuStrip;

	private ToolStripMenuItem fileToolStripMenuItem;

	private ToolStripMenuItem exitToolStripMenuItem;

	private ToolStripMenuItem helpToolStripMenuItem;

	private ToolStripMenuItem aboutToolStripMenuItem;

	private Button runPauseButton;

	private Button buildButton;

	private Label dekTecDeviceStatusLabel;

	private ToolStripMenuItem configurationToolStripMenuItem;

	private ToolStripSeparator separatorToolStripMenuItem;

	private PictureBox philipsPictureBox;

	public MainForm()
	{
		InitializeComponent();
		InitializeCustom();
	}

	public void InitializeCustom()
	{
		Console.WriteLine("MainForm: initializing");
		Text = Resources.GLOBAL_AppName;
		fileToolStripMenuItem.Text = Resources.MENU_File;
		configurationToolStripMenuItem.Text = Resources.MENU_FileConfiguration;
		exitToolStripMenuItem.Text = Resources.MENU_FileExit;
		helpToolStripMenuItem.Text = Resources.MENU_Help;
		aboutToolStripMenuItem.Text = Resources.MENU_HelpAbout;
		runPauseButton.Text = Resources.BTN_PlayTs;
		buildButton.Text = Resources.BTN_BuildTs;
		philipsPictureBox.Image = Resources.Philips;
		CheckForDekTecAssembly();
		_dekTecManager = DekTecManager.Instance;
		_buildLog = null;
		_buildProgressDialog = new BuildProgressDialog();
		_ = _buildProgressDialog.Handle;
		_configDialog = new ConfigurationDialog();
		_catalog = Catalog.CreateFromFile();
		Console.WriteLine("MainForm: default catalog has item(s) -  " + _catalog.NumberOfEntries);
		_tsPlayer = TsPlayer.Instance;
		if (_tsPlayer.State == TsPlayer.TsPlayerState.Paused && _tsPlayer.WasTsPlayInProgress())
		{
			_tsPlayer.Play(OnErrorPlayStreamCallback);
		}
		UpdateDekTecDeviceStatusDisplay();
		UpdateControls();
	}

	private void CheckForDekTecAssembly()
	{
		try
		{
			Assembly.Load("DTAPINET");
		}
		catch (Exception ex)
		{
			Console.WriteLine("MainForm: Fatal Error - failed to load DekTec assembly or supporting libraries: " + ex);
			AddToBuildLog("MainForm: Fatal Error - failed to load DekTec assembly or supporting libraries: " + ex);
			Environment.Exit(0);
		}
	}

	private void UpdateDekTecDeviceStatusDisplay()
	{
		if (base.InvokeRequired)
		{
			Invoke(new Invoke_UpdateDekTecDeviceStatusDisplay(UpdateDekTecDeviceStatusDisplay));
			return;
		}
		string deviceType = _dekTecManager.DeviceType;
		if (string.IsNullOrEmpty(deviceType))
		{
			dekTecDeviceStatusLabel.Text = Resources.LBL_DekTecDeviceNotAvailable;
			return;
		}
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.AppendFormat(Resources.LBL_DekTecDeviceAvailable, deviceType);
		if (_tsPlayer.State == TsPlayer.TsPlayerState.Running)
		{
			Config currentConfig = ConfigFactory.CurrentConfig;
			stringBuilder.AppendFormat(Resources.LBL_DekTecDeviceConfiguration, currentConfig.ModulationTypeAsString, currentConfig.ModulationFrequency, currentConfig.ConstellationAsString);
		}
		dekTecDeviceStatusLabel.Text = stringBuilder.ToString();
	}

	private void UpdateControls()
	{
		if (base.InvokeRequired)
		{
			Invoke(new Invoke_UpdateControls(UpdateControls));
			return;
		}
		switch (_tsPlayer.State)
		{
		case TsPlayer.TsPlayerState.NoTs:
			buildButton.Enabled = true;
			runPauseButton.Text = Resources.BTN_PlayTs;
			runPauseButton.Enabled = false;
			configurationToolStripMenuItem.Enabled = true;
			break;
		case TsPlayer.TsPlayerState.Paused:
			buildButton.Enabled = true;
			runPauseButton.Text = Resources.BTN_PlayTs;
			runPauseButton.Enabled = true;
			configurationToolStripMenuItem.Enabled = true;
			break;
		case TsPlayer.TsPlayerState.Running:
			buildButton.Enabled = false;
			runPauseButton.Text = Resources.BTN_StopTs;
			runPauseButton.Enabled = true;
			configurationToolStripMenuItem.Enabled = false;
			break;
		default:
			Console.WriteLine("MainForm: unhandled TsPlayer state " + _tsPlayer.State);
			break;
		}
	}

	private ScheduleEvent[] ProcessCopyBuildAction(string rootFolder, CatalogEntry entry)
	{
		Console.WriteLine("MainForm: processing copy build action");
		AddToBuildLog("TV Model requires a copy build action");
		List<ScheduleEvent> list = new List<ScheduleEvent>();
		string baseFolder = rootFolder + "\\" + entry.BaseFolder;
		FileSet fileSet = entry.FileSets[0];
		try
		{
			if (!fileSet.IncludedInBuild)
			{
				throw new InvalidDataException("The required FileSet is marked as excluded from the build");
			}
			string text = fileSet.PrepareUsageArgument(baseFolder);
			string text2 = ConfigFactory.CurrentConfig.ScheduleDataFolder + "\\" + entry.OutputFileName;
			Console.WriteLine("MainForm: copying file\n          from: {0}\n            to: {1}", text, text2);
			AddToBuildLog($"Copying from {text}");
			AddToBuildLog($"          to {text2}");
			_buildProgressDialog.SetAction(Resources.DLG_BuildActionCopyingMessage);
			File.Copy(text, text2);
			ScheduleEvent[] defaultScheduleEvents = entry.DefaultScheduleEvents;
			for (int i = 0; i < defaultScheduleEvents.Length; i++)
			{
				ScheduleEvent scheduleEvent = defaultScheduleEvents[i].Clone();
				scheduleEvent.TransportStreamFile = text2;
				list.Add(scheduleEvent);
			}
			Console.WriteLine("Mainform: suggested {0} new schedule event(s)", list.Count);
			AddToBuildLog("Build action completed");
		}
		catch (Exception ex)
		{
			Console.WriteLine("MainForm: Error - build action failed: {0}", ex);
			AddToBuildLog($"Build action failed with exception: {ex.Message}");
			list.Clear();
		}
		finally
		{
			fileSet.ReleaseUsageArgument(baseFolder);
		}
		return list.ToArray();
	}

	private void GenerateBuildProgressCB(TSGen.TS_ProgressMessage message)
	{
		try
		{
			switch (message)
			{
			case TSGen.TS_ProgressMessage.TS_PROGRESS_MESSAGE_PREPARING_CHANNEL_TABLE:
				_buildProgressDialog.SetAction(Resources.DLG_BuildActionPreparingChannelTableMessage);
				AddToBuildLog("Preparing Channel Table");
				break;
			case TSGen.TS_ProgressMessage.TS_PROGRESS_MESSAGE_PREPARING_NVM:
				_buildProgressDialog.SetAction(Resources.DLG_BuildActionPreparingNvmMessage);
				AddToBuildLog("Preparing NVM");
				break;
			case TSGen.TS_ProgressMessage.TS_PROGRESS_MESSAGE_PREPARING_THEME:
				_buildProgressDialog.SetAction(Resources.DLG_BuildActionPreparingThemeMessage);
				AddToBuildLog("Preparing Theme");
				break;
			case TSGen.TS_ProgressMessage.TS_PROGRESS_MESSAGE_GENERATING_TRANSPORT_STREAM:
				_buildProgressDialog.SetAction(Resources.DLG_BuildActionGeneratingTransportStreamMessage);
				AddToBuildLog("Generating transport stream");
				break;
			case TSGen.TS_ProgressMessage.TS_PROGRESS_MESSAGE_MERGING_TRANSPORT_STREAM:
				_buildProgressDialog.SetAction(Resources.DLG_BuildActionMergingTransportStreamMessage);
				AddToBuildLog("Generating transport stream");
				break;
			default:
				AddToBuildLog("Unknown progress message reported");
				break;
			}
		}
		catch (Exception)
		{
		}
	}

	private bool ProcessGenerateBuildAction(string rootFolder, CatalogEntry entry)
	{
		bool result = false;
		Config currentConfig = ConfigFactory.CurrentConfig;
		TSGen.TS_FileDetails? tS_FileDetails = null;
		TSGen.TS_FileDetails? tS_FileDetails2 = null;
		List<TSGen.TS_FileDetails> list = new List<TSGen.TS_FileDetails>();
		Console.WriteLine("MainForm: processing generate build action");
		AddToBuildLog("TV Model requires a generate build action");
		string text = rootFolder + "\\" + entry.BaseFolder;
		string text2 = text + "\\" + entry.OutputFileName;
		Console.WriteLine("entry.BaseFolder = " + entry.BaseFolder);
		Console.WriteLine("entry.OutputFileName = " + entry.OutputFileName);
		string baseFolder = entry.BaseFolder;
		string value = "2K14";
		string value2 = "2K15";
		string value3 = "2K16";
		string text3 = "0000";
		bool flag = false;
		if (baseFolder.IndexOf(value) > -1)
		{
			Console.WriteLine("2K14 Model");
			flag = true;
			text3 = "0000";
		}
		else if (baseFolder.IndexOf(value2) > -1)
		{
			Console.WriteLine("2K15 Model");
			flag = true;
			text3 = "00000";
		}
		else if (baseFolder.IndexOf(value3) > -1)
		{
			Console.WriteLine("2K16 Model");
			flag = true;
			text3 = "00000";
		}
		else
		{
			flag = false;
			text3 = currentConfig.RoomNumber;
		}
		Console.WriteLine("use_room_number_string = " + flag);
		TSGen.TS_NetworkDetails networkDetails = GetNetworkDetails(currentConfig);
		try
		{
			FileSet[] fileSets = entry.FileSets;
			foreach (FileSet fileSet in fileSets)
			{
				if (fileSet.IsValid)
				{
					switch (fileSet.ID)
					{
					case FileSet.FileSetID.Easy2K11Clone:
						Console.WriteLine("FileSet.FileSetID.Easy2K11Clone filePath" + fileSet.PrepareUsageArgument(text));
						list.Add(new TSGen.TS_FileDetails?(new TSGen.TS_FileDetails
						{
							filePath = fileSet.PrepareUsageArgument(text),
							fileType = TSGen.TS_FileType.Clone,
							fileVersionDetails = fileSet.GetVersionDetails(),
							channel_table_identifier = currentConfig.ChannelTableIdentifier,
							room_number = ushort.Parse(text3, NumberStyles.HexNumber),
							room_number_string = currentConfig.RoomNumber,
							ssb_identifier = currentConfig.SsbIdentifier
						}).Value);
						break;
					case FileSet.FileSetID.Easy2K11Firmware:
						Console.WriteLine("FileSet.FileSetID.Easy2K11Firmware filePath" + fileSet.PrepareUsageArgument(text));
						list.Add(new TSGen.TS_FileDetails?(new TSGen.TS_FileDetails
						{
							filePath = fileSet.PrepareUsageArgument(text),
							fileType = TSGen.TS_FileType.Upgrade,
							fileVersionDetails = fileSet.GetVersionDetails(),
							channel_table_identifier = currentConfig.ChannelTableIdentifier,
							room_number = ushort.Parse(text3, NumberStyles.HexNumber),
							room_number_string = currentConfig.RoomNumber,
							ssb_identifier = currentConfig.SsbIdentifier
						}).Value);
						break;
					default:
						throw new InvalidDataException("Invalid FileSet ID");
					}
				}
				Console.WriteLine("config.RoomNumber = " + currentConfig.RoomNumber);
				Console.WriteLine("room_number = " + ushort.Parse(text3, NumberStyles.HexNumber));
				Console.WriteLine("room_number_string = " + currentConfig.RoomNumber);
			}
			if (list.Count > 0)
			{
				TSGen.TS_Result tS_Result = TSGen.TS_Generate(GenerateBuildProgressCB, text2, list.ToArray(), list.Count, networkDetails);
				if (tS_Result == TSGen.TS_Result.TS_RESULT_OK)
				{
					AddToBuildLog($"Generated stream saved to {text2}");
					AddToBuildLog("Build action completed");
					result = true;
				}
				else
				{
					AddToBuildLog($"Build action failed with {tS_Result}");
				}
			}
		}
		catch (Exception ex)
		{
			Console.WriteLine("MainForm: Error - build action failed: {0}", ex);
			AddToBuildLog($"Build action failed with exception: {ex.Message}");
		}
		finally
		{
			FileSet[] fileSets = entry.FileSets;
			foreach (FileSet fileSet2 in fileSets)
			{
				if (fileSet2.IncludedInBuild)
				{
					fileSet2.ReleaseUsageArgument(text);
				}
			}
		}
		return result;
	}

	private static TSGen.TS_NetworkDetails GetNetworkDetails(Config config)
	{
		return new TSGen.TS_NetworkDetails
		{
			original_network_id = ushort.Parse(config.OriginalNetworkId, NumberStyles.HexNumber),
			centre_frequency = (uint)config.ModulationFrequency,
			bandwidth = config.BandwidthTs,
			constellation = config.ConstellationTs,
			code_rate = config.CodeRateTs,
			guard_interval = config.GuardIntervalTs,
			transmission_mode = config.TransmissionModeTs,
			prognum = ushort.Parse(config.ProgNum, NumberStyles.HexNumber),
			pid = ushort.Parse(config.Pid, NumberStyles.HexNumber),
			oui = uint.Parse(config.Oui, NumberStyles.HexNumber),
			txnid = uint.Parse(config.TxnId, NumberStyles.HexNumber),
			dwnldid = uint.Parse(config.DwnldId, NumberStyles.HexNumber),
			network_id = ushort.Parse(config.NetworkId, NumberStyles.HexNumber),
			transport_stream_id = ushort.Parse(config.TransportStreamId, NumberStyles.HexNumber),
			pcrpid = ushort.Parse(config.PcrPid, NumberStyles.HexNumber)
		};
	}

	private bool ProcessMergeBuildAction(List<TSGen.TS_FileDetails> allTsFileDetails)
	{
		Console.WriteLine("MainForm: processing merge build action");
		bool result = false;
		Config currentConfig = ConfigFactory.CurrentConfig;
		string text = ConfigFactory.CurrentConfig.OutputDataFolder + "\\" + Settings.MultiplexedTsFileName;
		TSGen.TS_NetworkDetails networkDetails = GetNetworkDetails(currentConfig);
		try
		{
			TSGen.TS_Result tS_Result = ((allTsFileDetails == null || allTsFileDetails.Count <= 0) ? TSGen.TS_Result.TS_RESULT_ERROR_NO_FILES : TSGen.TS_Merge(GenerateBuildProgressCB, text, allTsFileDetails.ToArray(), allTsFileDetails.Count, networkDetails));
			if (tS_Result == TSGen.TS_Result.TS_RESULT_OK)
			{
				AddToBuildLog($"Generated merged stream saved to {text}");
				AddToBuildLog("Build action completed");
				result = true;
			}
			else
			{
				AddToBuildLog($"Build action failed with {tS_Result}");
			}
		}
		catch (Exception ex)
		{
			Console.WriteLine("MainForm: Error - build action failed: {0}", ex);
			AddToBuildLog($"Build action failed with exception: {ex.Message}");
		}
		return result;
	}

	private bool PrepareBuildOutputFolders()
	{
		Config currentConfig = ConfigFactory.CurrentConfig;
		string previousOutputDataFolder = currentConfig.PreviousOutputDataFolder;
		string outputDataFolder = currentConfig.OutputDataFolder;
		_buildProgressDialog.SetAction(Resources.DLG_BuildActionPreparingFoldersMessage);
		try
		{
			if (Directory.Exists(previousOutputDataFolder))
			{
				Directory.Delete(previousOutputDataFolder, recursive: true);
			}
			if (Directory.Exists(outputDataFolder))
			{
				Directory.Delete(outputDataFolder, recursive: true);
			}
			Console.WriteLine("MainForm: creating new output folder\n          " + outputDataFolder);
			Thread.Sleep(500);
			Directory.CreateDirectory(outputDataFolder);
			Thread.Sleep(500);
		}
		catch (Exception ex)
		{
			Console.WriteLine("MainForm: build output folder preparation failed with exception: " + ex);
			return false;
		}
		return true;
	}

	private void OpenBuildLog()
	{
		if (_buildLog != null)
		{
			Console.WriteLine("MainForm: Error - trying to open a build log when one is already in use");
			return;
		}
		try
		{
			_buildLog = File.CreateText(ConfigFactory.CurrentConfig.BuildLogFile);
		}
		catch (Exception ex)
		{
			Console.WriteLine("MainForm: Error - failed to open build log file: " + ex);
			_buildLog = null;
		}
	}

	public void AddToBuildLog(string text)
	{
		if (_buildLog != null)
		{
			_buildLog.WriteLine(text);
		}
	}

	private void CloseBuildLog()
	{
		if (_buildLog != null)
		{
			_buildLog.Close();
			_buildLog = null;
		}
	}

	private static void DeleteFiles(string directoryPath, string extention)
	{
		string[] files = Directory.GetFiles(directoryPath, extention);
		for (int i = 0; i < files.Length; i++)
		{
			File.Delete(files[i]);
		}
	}

	private bool ProcessBuild()
	{
		Config currentConfig = ConfigFactory.CurrentConfig;
		string buildRootFolder = currentConfig.BuildRootFolder;
		string text = string.Empty;
		Console.WriteLine("MainForm: starting build process with root folder " + buildRootFolder);
		string empty = string.Empty;
		List<TSGen.TS_FileDetails> list = new List<TSGen.TS_FileDetails>();
		bool flag = false;
		string text2 = string.Empty;
		try
		{
			_tsPlayer.Pause();
			OpenBuildLog();
			AddToBuildLog("============================================================");
			AddToBuildLog($"{Resources.GLOBAL_AppName}, Version {Assembly.GetExecutingAssembly().GetName().Version}");
			AddToBuildLog("============================================================");
			AddToBuildLog(string.Empty);
			AddToBuildLog($"Build started at {DateTime.Now}");
			AddToBuildLog($"Input folder is {buildRootFolder}");
			AddToBuildLog($"Output folder is {currentConfig.ScheduleDataFolder}");
			CatalogEntry[] validCandidates = _catalog.GetValidCandidates(buildRootFolder, _buildLog);
			AddToBuildLog(string.Empty);
			AddToBuildLog($"{validCandidates.Count()} candidate(s) to build");
			if (validCandidates != null && validCandidates.Count() > 0)
			{
				DeleteFiles(Environment.CurrentDirectory, "*.dat");
				CatalogEntry[] array = validCandidates;
				foreach (CatalogEntry catalogEntry in array)
				{
					Console.WriteLine("MainForm: building for " + catalogEntry.TargetTvModel);
					AddToBuildLog(string.Empty);
					AddToBuildLog($"Building for {catalogEntry.TargetTvModel}");
					_buildProgressDialog.SetTvModel(string.Format(Resources.DLG_BuildTargetTvModelMessage, catalogEntry.TargetTvModel));
					_buildProgressDialog.SetAction(Resources.DLG_BuildActionStarting);
					text2 += $"{catalogEntry.TargetTvModel} ";
					empty = buildRootFolder + "\\" + catalogEntry.BaseFolder;
					switch (catalogEntry.Action)
					{
					case CatalogEntry.BuildAction.GenerateAndMerge:
						flag = ProcessGenerateBuildAction(buildRootFolder, catalogEntry);
						text = empty + "\\" + catalogEntry.OutputFileName;
						break;
					case CatalogEntry.BuildAction.Merge:
					{
						AddToBuildLog($"TV Model {catalogEntry.TargetTvModel} requires {catalogEntry.Action} action");
						FileSet[] fileSets = catalogEntry.FileSets;
						foreach (FileSet fileSet in fileSets)
						{
							if (fileSet.IsValid)
							{
								text = fileSet.PrepareUsageArgument(empty);
							}
						}
						flag = true;
						break;
					}
					default:
						Console.WriteLine("MainForm: Error - unhandled build action " + catalogEntry.Action);
						AddToBuildLog($"Unhandled build action with value {catalogEntry.Action}");
						flag = false;
						break;
					}
					if (!flag)
					{
						continue;
					}
					if (_tsPlayer.IsValidTsFile(text))
					{
						if (text.Contains(".ts"))
						{
							list.Add(new TSGen.TS_FileDetails
							{
								filePath = text,
								fileType = TSGen.TS_FileType.TransportStream
							});
						}
						else if (text.Contains("Cloning.upg"))
						{
							list.Add(new TSGen.TS_FileDetails
							{
								filePath = text,
								fileType = TSGen.TS_FileType.Clone
							});
						}
						else if (text.Contains("run.upg"))
						{
							list.Add(new TSGen.TS_FileDetails
							{
								filePath = text,
								fileType = TSGen.TS_FileType.Upgrade
							});
						}
						else if (text.Contains(".trp"))
						{
							list.Add(new TSGen.TS_FileDetails
							{
								filePath = text,
								fileType = TSGen.TS_FileType.TransportStream
							});
						}
						else
						{
							Console.WriteLine("Unknown upg or trp or ts file");
						}
					}
					else
					{
						AddToBuildLog($"Invalid TS file found {text} for TV modle {catalogEntry.TargetTvModel}");
						flag = false;
					}
				}
				if (flag)
				{
					_buildProgressDialog.SetTvModel(string.Format(Resources.DLG_BuildTargetTvModelMessage, text2));
					flag = ProcessMergeBuildAction(list);
				}
			}
			UpdateControls();
		}
		catch (Exception ex)
		{
			Console.WriteLine("MainForm: Error - an exception occurred during the build:  " + ex);
			AddToBuildLog($"Exception during build: {ex.Message}");
			flag = false;
		}
		finally
		{
			AddToBuildLog(string.Empty);
			AddToBuildLog(flag ? "Build successful" : "Build failed");
			AddToBuildLog($"Build finished at {DateTime.Now}");
			CloseBuildLog();
		}
		return flag;
	}

	private void ProcessBuildWorker(object stateInfo)
	{
		try
		{
			if (!PrepareBuildOutputFolders())
			{
				SystemSounds.Beep.Play();
				AddToBuildLog(Resources.DLG_PreBuildFailedMessage);
				_buildProgressDialog.SetResult(Resources.DLG_PreBuildFailedMessage, viewLogButtonVisible: false);
			}
			else if (!ProcessBuild())
			{
				SystemSounds.Beep.Play();
				AddToBuildLog(Resources.DLG_BuildFailedMessage);
				_buildProgressDialog.SetResult(Resources.DLG_BuildFailedMessage, viewLogButtonVisible: true);
			}
			else
			{
				AddToBuildLog(Resources.DLG_BuildSuccessfulMessage);
				AddToBuildLog("Build Log is placed in the Outout folder");
			}
		}
		catch (Exception)
		{
		}
	}

	private void CleanUpOnExit()
	{
		_tsPlayer.Pause();
		_dekTecManager.Dispose();
	}

	private void exitToolStripMenuItem_Click(object sender, EventArgs eventArgs)
	{
		Close();
	}

	private void MainForm_FormClosing(object sender, FormClosingEventArgs eventArgs)
	{
		CleanUpOnExit();
	}

	private void aboutToolStripMenuItem_Click(object sender, EventArgs eventArgs)
	{
		new AboutBox().Show(this);
	}

	private void configurationToolStripMenuItem_Click(object sender, EventArgs eventArgs)
	{
		Config currentConfig = ConfigFactory.CurrentConfig;
		_ = currentConfig.WorkingRootFolder;
		if (_configDialog.ShowDialog(this, currentConfig))
		{
			currentConfig.SaveToFile();
		}
	}

	public void PlayPauseButton_Click(object sender, EventArgs eventArgs)
	{
		switch (_tsPlayer.State)
		{
		case TsPlayer.TsPlayerState.Paused:
			_tsPlayer.Play(OnErrorPlayStreamCallback);
			break;
		case TsPlayer.TsPlayerState.Running:
			_tsPlayer.Pause();
			break;
		default:
			Console.WriteLine("MainForm: unhandled TsPlayer state  " + _tsPlayer.State);
			break;
		case TsPlayer.TsPlayerState.NoTs:
			break;
		}
		UpdateControls();
		UpdateDekTecDeviceStatusDisplay();
	}

	private void OnErrorPlayStreamCallback(string message, Exception exception)
	{
		AddToBuildLog(message + " " + Resources.GLOBAL_AppName.ToString());
		Console.WriteLine(message + " " + Resources.GLOBAL_AppName.ToString());
		_tsPlayer.Pause();
		UpdateControls();
	}

	public void BuildButton_Click(object sender, EventArgs eventArgs)
	{
		ProcessBuildWorker(null);
	}

	private void SaveDefaultConfiguration()
	{
		new Config().SaveToFile();
	}

	private void SaveDefaultCatalog()
	{
		Catalog catalog = new Catalog();
		List<CatalogEntry> list = new List<CatalogEntry>();
		List<FileSet> list2 = new List<FileSet>();
		List<string> list3 = new List<string>();
		List<ScheduleEvent> list4 = new List<ScheduleEvent>();
		list.Clear();
		CatalogEntry catalogEntry = new CatalogEntry
		{
			TargetTvModel = "2k10 Prime",
			BaseFolder = "2k10_Prime",
			OutputFileName = "2k10_Prime.ts",
			Action = CatalogEntry.BuildAction.Generate
		};
		list.Add(catalogEntry);
		list2.Clear();
		FileSet fileSet = new FileSet
		{
			ID = FileSet.FileSetID.PrimeUpgrade,
			Requirement = FileSet.FileSetRequirement.Optional,
			Usage = FileSet.FileSetUsage.UseFirstFile
		};
		list3.Clear();
		list3.Add("OAD_Q555H_*.upg");
		fileSet.Files = list3.ToArray();
		list2.Add(fileSet);
		fileSet = new FileSet
		{
			ID = FileSet.FileSetID.PrimeVSec,
			Requirement = FileSet.FileSetRequirement.Optional,
			Usage = FileSet.FileSetUsage.UseFirstFile
		};
		list3.Clear();
		list3.Add("vseckeys.txt");
		fileSet.Files = list3.ToArray();
		list2.Add(fileSet);
		fileSet = new FileSet
		{
			ID = FileSet.FileSetID.PrimeStandby,
			Requirement = FileSet.FileSetRequirement.Optional,
			Usage = FileSet.FileSetUsage.UseFirstFile,
			PrefixFolder = "upgrades"
		};
		list3.Clear();
		list3.Add("StandbySW_Q555H_CFG62_*.upg");
		fileSet.Files = list3.ToArray();
		list2.Add(fileSet);
		fileSet = new FileSet
		{
			ID = FileSet.FileSetID.PrimeNVM,
			Requirement = FileSet.FileSetRequirement.Optional,
			Usage = FileSet.FileSetUsage.UseInterimFolder,
			InterimFolder = "NVM"
		};
		list3.Clear();
		list3.Add("BDSSettings.txt");
		list3.Add("InstallationSettings.txt");
		list3.Add("LastStatus.txt");
		list3.Add("smartdata");
		list3.Add("sourcelib_ActivityTable");
		list3.Add("sourcelib_DevTable");
		list3.Add("sourcelib_PhyExtTable");
		fileSet.Files = list3.ToArray();
		list2.Add(fileSet);
		fileSet = new FileSet
		{
			ID = FileSet.FileSetID.PrimeChannelTable,
			PrefixFolder = "ChannelList\\channellib",
			Usage = FileSet.FileSetUsage.UsePrefixFolder,
			Requirement = FileSet.FileSetRequirement.Optional
		};
		list3.Clear();
		list3.Add("AntennaAnalogTable");
		list3.Add("AntennaDigSrvTable");
		list3.Add("AntennaDigTSTable");
		list3.Add("AntennaFrqMapTable");
		list3.Add("AntennaPresetTable");
		list3.Add("CableAnalogTable");
		list3.Add("CableDigSrvTable");
		list3.Add("CableDigTSTable");
		list3.Add("CableFrqMapTable");
		list3.Add("CablePresetTable");
		fileSet.Files = list3.ToArray();
		list2.Add(fileSet);
		fileSet = new FileSet
		{
			ID = FileSet.FileSetID.PrimeTheme,
			PrefixFolder = "ThemeTV",
			Usage = FileSet.FileSetUsage.UsePrefixFolder,
			Requirement = FileSet.FileSetRequirement.Optional
		};
		list3.Clear();
		list3.Add("background.png");
		list3.Add("footer.png");
		list3.Add("header.png");
		list3.Add("R1.png");
		list3.Add("R1S.png");
		list3.Add("R2.png");
		list3.Add("R2S.png");
		list3.Add("R3.png");
		list3.Add("R3S.png");
		list3.Add("R4.png");
		list3.Add("R4S.png");
		list3.Add("R5.png");
		list3.Add("R5S.png");
		list3.Add("R6.png");
		list3.Add("R6S.png");
		list3.Add("R7.png");
		list3.Add("R7S.png");
		list3.Add("R8.png");
		list3.Add("R8S.png");
		list3.Add("R9.png");
		list3.Add("R9S.png");
		list3.Add("R10.png");
		list3.Add("R10S.png");
		list3.Add("R11.png");
		list3.Add("R11S.png");
		list3.Add("R12.png");
		list3.Add("R12S.png");
		list3.Add("R13.png");
		list3.Add("R13S.png");
		list3.Add("R14.png");
		list3.Add("R14S.png");
		list3.Add("R15.png");
		list3.Add("R15S.png");
		fileSet.Files = list3.ToArray();
		list2.Add(fileSet);
		catalogEntry.FileSets = list2.ToArray();
		list4.Clear();
		list4.Add(new ScheduleEvent
		{
			StartTimeAsString = "03:45:00",
			EndTimeAsString = "07:45:00"
		});
		catalogEntry.DefaultScheduleEvents = list4.ToArray();
		catalogEntry = new CatalogEntry
		{
			TargetTvModel = "2k10 Easy 26\"",
			BaseFolder = "2k10_Easy",
			OutputFileName = "2k10_Easy_SS.ts",
			Action = CatalogEntry.BuildAction.Copy
		};
		list.Add(catalogEntry);
		list2.Clear();
		fileSet = new FileSet
		{
			ID = FileSet.FileSetID.EasyTransportStream,
			Requirement = FileSet.FileSetRequirement.Optional,
			Usage = FileSet.FileSetUsage.UseFirstFile
		};
		list3.Clear();
		list3.Add("2K10_ds_buh_ssu_*_*ONID.ts");
		fileSet.Files = list3.ToArray();
		list2.Add(fileSet);
		catalogEntry.FileSets = list2.ToArray();
		list4.Clear();
		list4.Add(new ScheduleEvent
		{
			StartTimeAsString = "00:00:00",
			EndTimeAsString = "02:00:00"
		});
		list4.Add(new ScheduleEvent
		{
			StartTimeAsString = "04:00:00",
			EndTimeAsString = "06:00:00"
		});
		list4.Add(new ScheduleEvent
		{
			StartTimeAsString = "08:00:00",
			EndTimeAsString = "10:00:00"
		});
		list4.Add(new ScheduleEvent
		{
			StartTimeAsString = "12:00:00",
			EndTimeAsString = "14:00:00"
		});
		list4.Add(new ScheduleEvent
		{
			StartTimeAsString = "16:00:00",
			EndTimeAsString = "18:00:00"
		});
		list4.Add(new ScheduleEvent
		{
			StartTimeAsString = "20:00:00",
			EndTimeAsString = "22:00:00"
		});
		catalogEntry.DefaultScheduleEvents = list4.ToArray();
		catalogEntry = new CatalogEntry
		{
			TargetTvModel = "2k10 Easy 32\"",
			BaseFolder = "2k10_Easy",
			OutputFileName = "2k10_Easy_LS.ts",
			Action = CatalogEntry.BuildAction.Copy
		};
		list.Add(catalogEntry);
		list2.Clear();
		fileSet = new FileSet
		{
			ID = FileSet.FileSetID.EasyTransportStream,
			Requirement = FileSet.FileSetRequirement.Optional,
			Usage = FileSet.FileSetUsage.UseFirstFile
		};
		list3.Clear();
		list3.Add("2K10_dl_buh_ssu_*_*ONID.ts");
		fileSet.Files = list3.ToArray();
		list2.Add(fileSet);
		catalogEntry.FileSets = list2.ToArray();
		list4.Clear();
		list4.Add(new ScheduleEvent
		{
			StartTimeAsString = "02:00:00",
			EndTimeAsString = "04:00:00"
		});
		list4.Add(new ScheduleEvent
		{
			StartTimeAsString = "06:00:00",
			EndTimeAsString = "08:00:00"
		});
		list4.Add(new ScheduleEvent
		{
			StartTimeAsString = "10:00:00",
			EndTimeAsString = "12:00:00"
		});
		list4.Add(new ScheduleEvent
		{
			StartTimeAsString = "14:00:00",
			EndTimeAsString = "16:00:00"
		});
		list4.Add(new ScheduleEvent
		{
			StartTimeAsString = "18:00:00",
			EndTimeAsString = "20:00:00"
		});
		list4.Add(new ScheduleEvent
		{
			StartTimeAsString = "22:00:00",
			EndTimeAsString = "1.00:00:00"
		});
		catalogEntry.DefaultScheduleEvents = list4.ToArray();
		catalog.Entries = list.ToArray();
		catalog.SaveToFile();
	}

	private void MainForm_Load(object sender, EventArgs e)
	{
	}

	protected override void Dispose(bool disposing)
	{
		if (disposing && components != null)
		{
			components.Dispose();
		}
		base.Dispose(disposing);
	}

	private void InitializeComponent()
	{
		System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(Philips.PSG.Gateway.MainForm));
		this.menuStrip = new System.Windows.Forms.MenuStrip();
		this.fileToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
		this.configurationToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
		this.separatorToolStripMenuItem = new System.Windows.Forms.ToolStripSeparator();
		this.exitToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
		this.helpToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
		this.aboutToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
		this.runPauseButton = new System.Windows.Forms.Button();
		this.buildButton = new System.Windows.Forms.Button();
		this.dekTecDeviceStatusLabel = new System.Windows.Forms.Label();
		this.philipsPictureBox = new System.Windows.Forms.PictureBox();
		this.menuStrip.SuspendLayout();
		((System.ComponentModel.ISupportInitialize)this.philipsPictureBox).BeginInit();
		base.SuspendLayout();
		this.menuStrip.Items.AddRange(new System.Windows.Forms.ToolStripItem[2] { this.fileToolStripMenuItem, this.helpToolStripMenuItem });
		this.menuStrip.Location = new System.Drawing.Point(0, 0);
		this.menuStrip.Name = "menuStrip";
		this.menuStrip.Size = new System.Drawing.Size(550, 24);
		this.menuStrip.TabIndex = 0;
		this.menuStrip.Text = "menuStrip";
		this.fileToolStripMenuItem.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[3] { this.configurationToolStripMenuItem, this.separatorToolStripMenuItem, this.exitToolStripMenuItem });
		this.fileToolStripMenuItem.Name = "fileToolStripMenuItem";
		this.fileToolStripMenuItem.Size = new System.Drawing.Size(37, 20);
		this.fileToolStripMenuItem.Text = "File";
		this.configurationToolStripMenuItem.Name = "configurationToolStripMenuItem";
		this.configurationToolStripMenuItem.Size = new System.Drawing.Size(148, 22);
		this.configurationToolStripMenuItem.Text = "Configuration";
		this.configurationToolStripMenuItem.Click += new System.EventHandler(configurationToolStripMenuItem_Click);
		this.separatorToolStripMenuItem.Name = "separatorToolStripMenuItem";
		this.separatorToolStripMenuItem.Size = new System.Drawing.Size(145, 6);
		this.exitToolStripMenuItem.Name = "exitToolStripMenuItem";
		this.exitToolStripMenuItem.Size = new System.Drawing.Size(148, 22);
		this.exitToolStripMenuItem.Text = "Exit";
		this.exitToolStripMenuItem.Click += new System.EventHandler(exitToolStripMenuItem_Click);
		this.helpToolStripMenuItem.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[1] { this.aboutToolStripMenuItem });
		this.helpToolStripMenuItem.Name = "helpToolStripMenuItem";
		this.helpToolStripMenuItem.Size = new System.Drawing.Size(44, 20);
		this.helpToolStripMenuItem.Text = "Help";
		this.aboutToolStripMenuItem.Name = "aboutToolStripMenuItem";
		this.aboutToolStripMenuItem.Size = new System.Drawing.Size(107, 22);
		this.aboutToolStripMenuItem.Text = "About";
		this.aboutToolStripMenuItem.Click += new System.EventHandler(aboutToolStripMenuItem_Click);
		this.runPauseButton.Location = new System.Drawing.Point(129, 35);
		this.runPauseButton.Name = "runPauseButton";
		this.runPauseButton.Size = new System.Drawing.Size(130, 23);
		this.runPauseButton.TabIndex = 1;
		this.runPauseButton.Text = "RunPause";
		this.runPauseButton.UseVisualStyleBackColor = true;
		this.runPauseButton.Click += new System.EventHandler(PlayPauseButton_Click);
		this.buildButton.Location = new System.Drawing.Point(291, 35);
		this.buildButton.Name = "buildButton";
		this.buildButton.Size = new System.Drawing.Size(130, 23);
		this.buildButton.TabIndex = 2;
		this.buildButton.Text = "Build";
		this.buildButton.UseVisualStyleBackColor = true;
		this.buildButton.Click += new System.EventHandler(BuildButton_Click);
		this.dekTecDeviceStatusLabel.AutoSize = true;
		this.dekTecDeviceStatusLabel.Location = new System.Drawing.Point(24, 443);
		this.dekTecDeviceStatusLabel.Name = "dekTecDeviceStatusLabel";
		this.dekTecDeviceStatusLabel.Size = new System.Drawing.Size(110, 13);
		this.dekTecDeviceStatusLabel.TabIndex = 4;
		this.dekTecDeviceStatusLabel.Text = "DekTecDeviceStatus";
		this.philipsPictureBox.Location = new System.Drawing.Point(457, 442);
		this.philipsPictureBox.Name = "philipsPictureBox";
		this.philipsPictureBox.Size = new System.Drawing.Size(65, 14);
		this.philipsPictureBox.SizeMode = System.Windows.Forms.PictureBoxSizeMode.CenterImage;
		this.philipsPictureBox.TabIndex = 19;
		this.philipsPictureBox.TabStop = false;
		base.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
		base.ClientSize = new System.Drawing.Size(550, 468);
		base.Controls.Add(this.philipsPictureBox);
		base.Controls.Add(this.dekTecDeviceStatusLabel);
		base.Controls.Add(this.buildButton);
		base.Controls.Add(this.runPauseButton);
		base.Controls.Add(this.menuStrip);
		this.DoubleBuffered = true;
		base.Enabled = false;
		base.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedSingle;
		base.Icon = (System.Drawing.Icon)resources.GetObject("$this.Icon");
		base.MainMenuStrip = this.menuStrip;
		base.MaximizeBox = false;
		base.Name = "MainForm";
		base.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
		this.Text = "MainForm";
		base.Load += new System.EventHandler(MainForm_Load);
		base.FormClosing += new System.Windows.Forms.FormClosingEventHandler(MainForm_FormClosing);
		this.menuStrip.ResumeLayout(false);
		this.menuStrip.PerformLayout();
		((System.ComponentModel.ISupportInitialize)this.philipsPictureBox).EndInit();
		base.ResumeLayout(false);
		base.PerformLayout();
	}
}
