using System;
using System.Collections.Generic;
using System.IO;

namespace Philips.PSG.Gateway;

[Serializable]
public class CatalogEntry
{
	public enum BuildAction
	{
		Undefined,
		Copy,
		Generate,
		Merge,
		GenerateAndMerge
	}

	private string _targetTvModel;

	private string _baseFolder;

	private string _outputFileName;

	private List<FileSet> _fileSets;

	private BuildAction _action;

	private List<ScheduleEvent> _defaultScheduleEvents;

	public string TargetTvModel
	{
		get
		{
			return _targetTvModel;
		}
		set
		{
			if (value == null)
			{
				_targetTvModel = string.Empty;
			}
			else
			{
				_targetTvModel = value;
			}
		}
	}

	public string BaseFolder
	{
		get
		{
			return _baseFolder;
		}
		set
		{
			if (value == null)
			{
				_baseFolder = string.Empty;
			}
			else
			{
				_baseFolder = value;
			}
		}
	}

	public string OutputFileName
	{
		get
		{
			return _outputFileName;
		}
		set
		{
			if (value == null)
			{
				_outputFileName = string.Empty;
			}
			else
			{
				_outputFileName = value;
			}
		}
	}

	public FileSet[] FileSets
	{
		get
		{
			return _fileSets.ToArray();
		}
		set
		{
			_fileSets.Clear();
			if (value != null)
			{
				_fileSets.AddRange(value);
			}
		}
	}

	public BuildAction Action
	{
		get
		{
			return _action;
		}
		set
		{
			_action = value;
		}
	}

	public ScheduleEvent[] DefaultScheduleEvents
	{
		get
		{
			return _defaultScheduleEvents.ToArray();
		}
		set
		{
			_defaultScheduleEvents.Clear();
			if (value == null)
			{
				return;
			}
			_defaultScheduleEvents.AddRange(value);
			foreach (ScheduleEvent defaultScheduleEvent in _defaultScheduleEvents)
			{
				defaultScheduleEvent.TargetTvModel = _targetTvModel;
			}
		}
	}

	public CatalogEntry()
	{
		_targetTvModel = string.Empty;
		_baseFolder = string.Empty;
		_outputFileName = string.Empty;
		_fileSets = new List<FileSet>();
		_action = BuildAction.Undefined;
		_defaultScheduleEvents = new List<ScheduleEvent>();
	}

	private void AddToLog(StreamWriter logStream, string text)
	{
		logStream?.WriteLine(text);
	}

	public bool IsValidCandidate(string rootFolder, StreamWriter logStream)
	{
		AddToLog(logStream, string.Empty);
		AddToLog(logStream, $"Checking candidate status for {_targetTvModel}");
		bool flag = false;
		string text = rootFolder + "\\" + _baseFolder;
		string baseFolder = _baseFolder;
		string text2 = "2k12_EasySuite";
		if (baseFolder.IndexOf(text2) > -1)
		{
			ConfigFactory.CurrentConfig.TsModelName = "2k12_EasySuite";
			AddToLog(logStream, $"Looking for root folder {text2}");
		}
		else
		{
			ConfigFactory.CurrentConfig.TsModelName = "none";
			AddToLog(logStream, string.Format("Looking for root folder {0}", "not 2k12_EasySuite"));
		}
		AddToLog(logStream, $"Looking for root folder {text}");
		if (Directory.Exists(text))
		{
			AddToLog(logStream, "Folder exists, looking for files");
			try
			{
				DirectoryInfo directoryInfo = new DirectoryInfo(text);
				int num = 0;
				int num2 = 0;
				bool flag2 = false;
				bool flag3 = false;
				foreach (FileSet fileSet in _fileSets)
				{
					bool flag4 = fileSet.Requirement == FileSet.FileSetRequirement.Required;
					List<string> list = new List<string>();
					string text3 = text + "\\" + fileSet.PrefixFolder;
					if (!string.IsNullOrEmpty(fileSet.PrefixFolder))
					{
						directoryInfo = new DirectoryInfo(text3);
					}
					string[] files = fileSet.Files;
					foreach (string text4 in files)
					{
						string item = ((!string.IsNullOrEmpty(fileSet.PrefixFolder)) ? (text3 + "\\" + text4) : (text + "\\" + text4));
						try
						{
							FileInfo[] files2 = directoryInfo.GetFiles(text4);
							if (files2.Length == 0)
							{
								list.Add(item);
								continue;
							}
							if (files2.Length == 1)
							{
								num++;
								flag4 = true;
								continue;
							}
							AddToLog(logStream, $"File {text4} has more than one match");
							num++;
							flag3 = true;
							flag4 = true;
						}
						catch (DirectoryNotFoundException)
						{
							list.Add(item);
						}
					}
					fileSet.IsValid = list.Count <= 0;
					foreach (string item2 in list)
					{
						AddToLog(logStream, $"File {item2} is missing");
					}
					if (flag4)
					{
						num2 += fileSet.NumberOfFiles;
						if (list.Count > 0)
						{
							flag2 = true;
						}
					}
					fileSet.IncludedInBuild = flag4;
				}
				if (flag3)
				{
					Console.WriteLine(string.Format(Resources.DLG_CatalogEntryInvalidMultipleFiles, _targetTvModel) + " " + Resources.GLOBAL_AppName.ToString());
					AddToLog(logStream, string.Format(Resources.DLG_CatalogEntryInvalidMultipleFiles, _targetTvModel) + " " + Resources.GLOBAL_AppName.ToString());
				}
				if (flag2)
				{
					Console.WriteLine(string.Format(Resources.DLG_CatalogEntryInvalidPartialFileSet, _targetTvModel) + " " + Resources.GLOBAL_AppName.ToString());
					AddToLog(logStream, string.Format(Resources.DLG_CatalogEntryInvalidPartialFileSet, _targetTvModel) + " " + Resources.GLOBAL_AppName.ToString());
				}
				if (num > 0 && num == num2 && !flag2 && !flag3)
				{
					flag = true;
				}
			}
			catch (Exception ex2)
			{
				AddToLog(logStream, $"Exception during candidate processing: {ex2.Message}");
				flag = false;
			}
		}
		else
		{
			AddToLog(logStream, "Folder does not exist");
		}
		if (flag)
		{
			AddToLog(logStream, "Atleast one required file exist");
			AddToLog(logStream, "Candidate is valid and will be included in the build");
		}
		else
		{
			AddToLog(logStream, "Candidate is not valid and will be excluded from the build");
		}
		return flag;
	}
}
