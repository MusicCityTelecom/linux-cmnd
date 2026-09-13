using System;
using System.IO;
using System.Text;
using System.Xml.Serialization;
using DTAPINET;

namespace Philips.PSG.Gateway;

[Serializable]
public class Config
{
	public const int OUTPUT_CHANNEL_MIN = 0;

	public const int OUTPUT_CHANNEL_MAX = 68;

	public const int OUTPUT_CHANNEL_DEFAULT = 50;

	public const int OUTPUT_CHANNEL_BANDWIDTH = 8;

	public const int OUTPUT_CHANNEL_MIN_FREQUENCY = 450;

	public const int OUTPUT_CHANNEL_MAX_FREQUENCY = 8640;

	public const int OUTPUT_CHANNEL_DEFAULT_FREQUENCY = 7060;

	public static string TRANSMISSION_MODE_2K_STRING = "2k";

	public static string TRANSMISSION_MODE_4K_STRING = "4k";

	public static string TRANSMISSION_MODE_8K_STRING = "8k";

	public static string GUARD_INTERVAL_1_4_STRING = "1/4";

	public static string GUARD_INTERVAL_1_8_STRING = "1/8";

	public static string GUARD_INTERVAL_1_16_STRING = "1/16";

	public static string GUARD_INTERVAL_1_32_STRING = "1/32";

	public static string CONSTELLATION_QPSK_STRING = "QPSK";

	public static string CONSTELLATION_QAM16_STRING = "16-QAM";

	public static string CONSTELLATION_QAM64_STRING = "64-QAM";

	public static string CODE_RATE_1_2_STRING = "1/2";

	public static string CODE_RATE_2_3_STRING = "2/3";

	public static string CODE_RATE_3_4_STRING = "3/4";

	public static string CODE_RATE_4_5_STRING = "4/5";

	public static string CODE_RATE_5_6_STRING = "5/6";

	public static string CODE_RATE_6_7_STRING = "6/7";

	public static string CODE_RATE_7_8_STRING = "7/8";

	public static string MODULATION_TYPE_DVBT_STRING = "DVB-T";

	public static string DEVICE_CAPABILITY_FLAG_DVBT_STRING = "DVB-T";

	public static string DEVICE_CAPABILITY_FLAG_UHF_STRING = "UHF";

	public static string DEVICE_STREAM_TYPE_TS_MOD = "MODULATED";

	public static string TX_MODE_188_STRING = "188";

	public static string TX_MODE_ADD16_STRING = "ADD-16";

	public static string TX_MODE_130_STRING = "130";

	public static string TX_MODE_192_STRING = "192";

	public static string TX_MODE_204_STRING = "204";

	public static string TX_MODE_MIN16_STRING = "MIN-16";

	public static string TX_MODE_RAW_STRING = "RAW";

	public static string STUFF_MODE_ON_STRING = "ON";

	public static string STUFF_MODE_OFF_STRING = "OFF";

	public static string BANDWIDTH_5MHZ_STRING = "5";

	public static string BANDWIDTH_6MHZ_STRING = "6";

	public static string BANDWIDTH_7MHZ_STRING = "7";

	public static string BANDWIDTH_8MHZ_STRING = "8";

	public string TsModelName = "none";

	private string _buildRootFolder = Settings.DefaultBuildRootFolder;

	private string _workingRootFolder = Settings.DefaultWorkingRootFolder;

	private DtCaps _deviceCapabilityFlags = DTAPI.CAP_TX_DVBT | DTAPI.CAP_UHF;

	private int _transmissionMode = 65536;

	private int _guardInterval = 256;

	private int _constellation = 48;

	private int _codeRate = 6;

	private int _modulationType = 9;

	private int _deviceStreamType = 16;

	private int _txMode = 17;

	private int _stuffMode;

	private int _bandwidth = 4;

	private int _outputChannel = 50;

	private int _modulationFrequency = 7060;

	private double _outputLevel = -27.5;

	public string BuildRootFolder
	{
		get
		{
			return _buildRootFolder;
		}
		set
		{
			if (value == null)
			{
				_buildRootFolder = string.Empty;
			}
			else
			{
				_buildRootFolder = value;
			}
		}
	}

	public string WorkingRootFolder
	{
		get
		{
			return _workingRootFolder;
		}
		set
		{
			if (value == null)
			{
				_workingRootFolder = string.Empty;
			}
			else
			{
				_workingRootFolder = value;
			}
		}
	}

	[XmlIgnore]
	public string ScheduleDataFolder => _workingRootFolder + "\\" + Settings.ScheduleDataFolderName;

	[XmlIgnore]
	public string OutputDataFolder => _workingRootFolder + "\\" + Settings.OutputDataFolderName;

	[XmlIgnore]
	public string PreviousScheduleDataFolder => _workingRootFolder + "\\" + Settings.PreviousScheduleDataFolderName;

	[XmlIgnore]
	public string PreviousOutputDataFolder => _workingRootFolder + "\\" + Settings.PreviousOutputDataFolderName;

	[XmlIgnore]
	public string ScheduleDataFile => ScheduleDataFolder + "\\" + Settings.ScheduleDataFileName;

	[XmlIgnore]
	public string MultiplexedTsFile => OutputDataFolder + "\\" + Settings.MultiplexedTsFileName;

	[XmlIgnore]
	public string ScheduleRunFile => ScheduleDataFolder + "\\" + Settings.ScheduleRunFileName;

	[XmlIgnore]
	public string TsPlayFile => OutputDataFolder + "\\" + Settings.TsPlayFileName;

	[XmlIgnore]
	public string BuildLogFile => ScheduleDataFolder + "\\" + Settings.BuildLogFileName;

	[XmlIgnore]
	public int TransmissionMode
	{
		get
		{
			return _transmissionMode;
		}
		set
		{
			_transmissionMode = value;
		}
	}

	public ushort TransmissionModeTs => _transmissionMode switch
	{
		65536 => 0, 
		131072 => 1, 
		196608 => 1, 
		_ => throw new Exception($"Unrecognised TransmissionMode value for TS: {_transmissionMode}"), 
	};

	[XmlElement("TransmissionMode")]
	public string TransmissionModeAsString
	{
		get
		{
			return _transmissionMode switch
			{
				65536 => TRANSMISSION_MODE_2K_STRING, 
				131072 => TRANSMISSION_MODE_4K_STRING, 
				196608 => TRANSMISSION_MODE_8K_STRING, 
				_ => throw new Exception($"Unrecognised TransmissionMode value: {_transmissionMode}"), 
			};
		}
		set
		{
			if (value.Equals(TRANSMISSION_MODE_2K_STRING))
			{
				_transmissionMode = 65536;
				return;
			}
			if (value.Equals(TRANSMISSION_MODE_4K_STRING))
			{
				_transmissionMode = 131072;
				return;
			}
			if (value.Equals(TRANSMISSION_MODE_8K_STRING))
			{
				_transmissionMode = 196608;
				return;
			}
			throw new InvalidDataException($"Unrecognised TransmissionMode string value: '{value}'");
		}
	}

	[XmlIgnore]
	public int GuardInterval
	{
		get
		{
			return _guardInterval;
		}
		set
		{
			_guardInterval = value;
		}
	}

	[XmlIgnore]
	public ushort GuardIntervalTs => _guardInterval switch
	{
		1024 => 3, 
		768 => 2, 
		512 => 1, 
		256 => 0, 
		_ => throw new Exception($"Unrecognised GuardInterval value for TS: {_guardInterval}"), 
	};

	[XmlElement("GuardInterval")]
	public string GuardIntervalAsString
	{
		get
		{
			return _guardInterval switch
			{
				1024 => GUARD_INTERVAL_1_4_STRING, 
				768 => GUARD_INTERVAL_1_8_STRING, 
				512 => GUARD_INTERVAL_1_16_STRING, 
				256 => GUARD_INTERVAL_1_32_STRING, 
				_ => throw new Exception($"Unrecognised GuardInterval value: {_guardInterval}"), 
			};
		}
		set
		{
			if (value.Equals(GUARD_INTERVAL_1_4_STRING))
			{
				_guardInterval = 1024;
				return;
			}
			if (value.Equals(GUARD_INTERVAL_1_8_STRING))
			{
				_guardInterval = 768;
				return;
			}
			if (value.Equals(GUARD_INTERVAL_1_16_STRING))
			{
				_guardInterval = 512;
				return;
			}
			if (value.Equals(GUARD_INTERVAL_1_32_STRING))
			{
				_guardInterval = 256;
				return;
			}
			throw new InvalidDataException($"Unrecognised GuardInterval string value: '{value}'");
		}
	}

	[XmlIgnore]
	public int Constellation
	{
		get
		{
			return _constellation;
		}
		set
		{
			_constellation = value;
		}
	}

	[XmlIgnore]
	public ushort ConstellationTs => _constellation switch
	{
		16 => 0, 
		32 => 1, 
		48 => 2, 
		_ => throw new Exception($"Unrecognised Constellation value for TS: {_constellation}"), 
	};

	[XmlElement("Constellation")]
	public string ConstellationAsString
	{
		get
		{
			return _constellation switch
			{
				16 => CONSTELLATION_QPSK_STRING, 
				32 => CONSTELLATION_QAM16_STRING, 
				48 => CONSTELLATION_QAM64_STRING, 
				_ => throw new Exception($"Unrecognised Constellation value: {_constellation}"), 
			};
		}
		set
		{
			if (value.Equals(CONSTELLATION_QPSK_STRING))
			{
				_constellation = 16;
				return;
			}
			if (value.Equals(CONSTELLATION_QAM16_STRING))
			{
				_constellation = 32;
				return;
			}
			if (value.Equals(CONSTELLATION_QAM64_STRING))
			{
				_constellation = 48;
				return;
			}
			throw new InvalidDataException($"Unrecognised Constellation string value: '{value}'");
		}
	}

	[XmlIgnore]
	public int CodeRate
	{
		get
		{
			return _codeRate;
		}
		set
		{
			_codeRate = value;
		}
	}

	[XmlIgnore]
	public ushort CodeRateTs => _codeRate switch
	{
		0 => 0, 
		1 => 1, 
		2 => 2, 
		4 => 3, 
		6 => 4, 
		_ => throw new Exception($"Unrecognised CodeRate value for TS: {_codeRate}"), 
	};

	[XmlElement("CodeRate")]
	public string CodeRateAsString
	{
		get
		{
			return _codeRate switch
			{
				0 => CODE_RATE_1_2_STRING, 
				1 => CODE_RATE_2_3_STRING, 
				2 => CODE_RATE_3_4_STRING, 
				3 => CODE_RATE_4_5_STRING, 
				4 => CODE_RATE_5_6_STRING, 
				5 => CODE_RATE_6_7_STRING, 
				6 => CODE_RATE_7_8_STRING, 
				_ => throw new Exception($"Unrecognised CodeRate value: {_codeRate}"), 
			};
		}
		set
		{
			if (value.Equals(CODE_RATE_1_2_STRING))
			{
				_codeRate = 0;
				return;
			}
			if (value.Equals(CODE_RATE_2_3_STRING))
			{
				_codeRate = 1;
				return;
			}
			if (value.Equals(CODE_RATE_3_4_STRING))
			{
				_codeRate = 2;
				return;
			}
			if (value.Equals(CODE_RATE_4_5_STRING))
			{
				_codeRate = 3;
				return;
			}
			if (value.Equals(CODE_RATE_5_6_STRING))
			{
				_codeRate = 4;
				return;
			}
			if (value.Equals(CODE_RATE_6_7_STRING))
			{
				_codeRate = 5;
				return;
			}
			if (value.Equals(CODE_RATE_7_8_STRING))
			{
				_codeRate = 6;
				return;
			}
			throw new InvalidDataException($"Unrecognised CodeRate string value: '{value}'");
		}
	}

	[XmlIgnore]
	public int ModulationType
	{
		get
		{
			return _modulationType;
		}
		set
		{
			_modulationType = value;
		}
	}

	[XmlElement("ModulationType")]
	public string ModulationTypeAsString
	{
		get
		{
			if (_modulationType == 9)
			{
				return MODULATION_TYPE_DVBT_STRING;
			}
			throw new Exception($"Unrecognised ModulationType value: {_modulationType}");
		}
		set
		{
			if (value.Equals(MODULATION_TYPE_DVBT_STRING))
			{
				_modulationType = 9;
				return;
			}
			throw new InvalidDataException($"Unrecognised ModulationType string value: '{value}'");
		}
	}

	[XmlIgnore]
	public DtCaps DeviceCapabilityFlags
	{
		get
		{
			return _deviceCapabilityFlags;
		}
		set
		{
			_deviceCapabilityFlags = value;
		}
	}

	[XmlElement("DeviceCapabilityFlags")]
	public string DeviceCapabilityFlagsAsString
	{
		get
		{
			StringBuilder stringBuilder = new StringBuilder();
			DtCaps deviceCapabilityFlags = _deviceCapabilityFlags;
			if ((deviceCapabilityFlags & DTAPI.CAP_TX_DVBT) == DTAPI.CAP_TX_DVBT)
			{
				stringBuilder.Append(DEVICE_CAPABILITY_FLAG_DVBT_STRING);
			}
			if ((deviceCapabilityFlags & DTAPI.CAP_UHF) == DTAPI.CAP_UHF)
			{
				if (stringBuilder.Length != 0)
				{
					stringBuilder.Append(" | ");
				}
				stringBuilder.Append(DEVICE_CAPABILITY_FLAG_UHF_STRING);
			}
			return stringBuilder.ToString();
		}
		set
		{
			DtCaps deviceCapabilityFlags = new DtCaps();
			string[] array = value.Split(new char[2] { '|', ' ' }, StringSplitOptions.RemoveEmptyEntries);
			if (array.Length == 0)
			{
				throw new InvalidDataException("Empty DeviceCapabilityFlags string value");
			}
			string[] array2 = array;
			foreach (string text in array2)
			{
				if (text.Equals(DEVICE_CAPABILITY_FLAG_DVBT_STRING))
				{
					deviceCapabilityFlags |= DTAPI.CAP_TX_DVBT;
					continue;
				}
				if (text.Equals(DEVICE_CAPABILITY_FLAG_UHF_STRING))
				{
					deviceCapabilityFlags |= DTAPI.CAP_UHF;
					continue;
				}
				throw new InvalidDataException($"Unrecognised DeviceCapabilityFlags string value: '{text}'");
			}
			_deviceCapabilityFlags = deviceCapabilityFlags;
		}
	}

	[XmlIgnore]
	public int DeviceStreamType
	{
		get
		{
			return _deviceStreamType;
		}
		set
		{
			_deviceStreamType = value;
		}
	}

	[XmlElement("DeviceStreamType")]
	public string DeviceStreamTypeAsString
	{
		get
		{
			if (_deviceStreamType == 16)
			{
				return DEVICE_STREAM_TYPE_TS_MOD;
			}
			throw new Exception($"Unrecognised DeviceStreamType value: {_deviceStreamType}");
		}
		set
		{
			if (value.Equals(DEVICE_STREAM_TYPE_TS_MOD))
			{
				_deviceStreamType = 16;
				return;
			}
			throw new InvalidDataException($"Unrecognised DeviceStreamType string value: '{value}'");
		}
	}

	[XmlIgnore]
	public int TxMode
	{
		get
		{
			return _txMode;
		}
		set
		{
			_txMode = value;
		}
	}

	[XmlElement("TxMode")]
	public string TxModeAsString
	{
		get
		{
			return _txMode switch
			{
				17 => TX_MODE_188_STRING, 
				20 => TX_MODE_ADD16_STRING, 
				18 => TX_MODE_192_STRING, 
				19 => TX_MODE_204_STRING, 
				21 => TX_MODE_MIN16_STRING, 
				23 => TX_MODE_RAW_STRING, 
				_ => throw new Exception($"Unrecognised TxMode value: {_txMode}"), 
			};
		}
		set
		{
			if (value.Equals(TX_MODE_188_STRING))
			{
				_txMode = 17;
				return;
			}
			if (value.Equals(TX_MODE_ADD16_STRING))
			{
				_txMode = 20;
				return;
			}
			if (value.Equals(TX_MODE_192_STRING))
			{
				_txMode = 18;
				return;
			}
			if (value.Equals(TX_MODE_204_STRING))
			{
				_txMode = 19;
				return;
			}
			if (value.Equals(TX_MODE_MIN16_STRING))
			{
				_txMode = 21;
				return;
			}
			if (value.Equals(TX_MODE_RAW_STRING))
			{
				_txMode = 23;
				return;
			}
			throw new InvalidDataException($"Unrecognised TxMode string value: '{value}'");
		}
	}

	[XmlIgnore]
	public int StuffMode
	{
		get
		{
			return _stuffMode;
		}
		set
		{
			_stuffMode = value;
		}
	}

	[XmlElement("StuffMode")]
	public string StuffModeAsString
	{
		get
		{
			return _stuffMode switch
			{
				1 => STUFF_MODE_ON_STRING, 
				0 => STUFF_MODE_OFF_STRING, 
				_ => throw new Exception($"Unrecognised StuffMode value: {_stuffMode}"), 
			};
		}
		set
		{
			if (value.Equals(STUFF_MODE_ON_STRING))
			{
				_stuffMode = 1;
				return;
			}
			if (value.Equals(STUFF_MODE_OFF_STRING))
			{
				_stuffMode = 0;
				return;
			}
			throw new InvalidDataException($"Unrecognised StuffMode string value: '{value}'");
		}
	}

	[XmlIgnore]
	public int Bandwidth
	{
		get
		{
			return _bandwidth;
		}
		set
		{
			_bandwidth = value;
		}
	}

	[XmlIgnore]
	public ushort BandwidthTs => _bandwidth switch
	{
		2 => 2, 
		3 => 1, 
		4 => 0, 
		_ => throw new Exception($"Unrecognised Bandwidth value for TS: {_bandwidth}"), 
	};

	[XmlElement("Bandwidth")]
	public string BandwidthAsString
	{
		get
		{
			return _bandwidth switch
			{
				1 => BANDWIDTH_5MHZ_STRING, 
				2 => BANDWIDTH_6MHZ_STRING, 
				3 => BANDWIDTH_7MHZ_STRING, 
				4 => BANDWIDTH_8MHZ_STRING, 
				_ => throw new Exception($"Unrecognised Bandwidth value: {_bandwidth}"), 
			};
		}
		set
		{
			if (value.Equals(BANDWIDTH_5MHZ_STRING))
			{
				_bandwidth = 1;
				return;
			}
			if (value.Equals(BANDWIDTH_6MHZ_STRING))
			{
				_bandwidth = 2;
				return;
			}
			if (value.Equals(BANDWIDTH_7MHZ_STRING))
			{
				_bandwidth = 3;
				return;
			}
			if (value.Equals(BANDWIDTH_8MHZ_STRING))
			{
				_bandwidth = 4;
				return;
			}
			throw new InvalidDataException($"Unrecognised Bandwidth string value: '{value}'");
		}
	}

	public int OutputChannel
	{
		get
		{
			return _outputChannel;
		}
		set
		{
			if (value < 0)
			{
				_outputChannel = 0;
			}
			else if (value > 68)
			{
				_outputChannel = 68;
			}
			else
			{
				_outputChannel = value;
			}
		}
	}

	public int ModulationFrequency
	{
		get
		{
			return _modulationFrequency;
		}
		set
		{
			if (value < 450)
			{
				_modulationFrequency = 450;
			}
			else if (value > 8640)
			{
				_modulationFrequency = 8640;
			}
			else
			{
				_modulationFrequency = value;
			}
		}
	}

	public double OutputLevel
	{
		get
		{
			return _outputLevel;
		}
		set
		{
			_outputLevel = value;
		}
	}

	[XmlElement("OriginalNetworkId")]
	public string OriginalNetworkId { get; set; }

	[XmlElement("ProgNum")]
	public string ProgNum { get; set; }

	[XmlElement("Pid")]
	public string Pid { get; set; }

	[XmlElement("Oui")]
	public string Oui { get; set; }

	[XmlElement("TxnId")]
	public string TxnId { get; set; }

	[XmlElement("DwnldId")]
	public string DwnldId { get; set; }

	[XmlElement("NetworkId")]
	public string NetworkId { get; set; }

	[XmlElement("TransportStreamId")]
	public string TransportStreamId { get; set; }

	[XmlElement("PcrPid")]
	public string PcrPid { get; set; }

	[XmlElement("Onid")]
	public ushort Onid { get; set; }

	[XmlElement("RoomNumber")]
	public string RoomNumber { get; set; }

	[XmlElement("SsbIdentifier")]
	public string SsbIdentifier { get; set; }

	[XmlElement("ChannelTableIdentifier")]
	public string ChannelTableIdentifier { get; set; }

	public static int ConvertChannelToFrequency(int channel)
	{
		return channel * 8 + 45;
	}

	public static Config CreateFromFile()
	{
		StreamReader streamReader = null;
		Config result = null;
		XmlSerializer xmlSerializer = new XmlSerializer(typeof(Config));
		try
		{
			streamReader = File.OpenText(Settings.ConfigFile);
			result = (Config)xmlSerializer.Deserialize(streamReader);
		}
		catch (Exception arg)
		{
			Console.WriteLine("Config: Error - failed to load configuration from file, using defaults: {0}", arg);
			result = new Config();
		}
		finally
		{
			streamReader?.Close();
		}
		return result;
	}

	public void SaveToFile()
	{
		string configFile = Settings.ConfigFile;
		StreamWriter streamWriter = null;
		XmlSerializer xmlSerializer = new XmlSerializer(typeof(Config));
		try
		{
			Directory.CreateDirectory(Path.GetDirectoryName(configFile));
			streamWriter = File.CreateText(configFile);
			xmlSerializer.Serialize(streamWriter, this);
			streamWriter.Flush();
		}
		catch (Exception arg)
		{
			Console.WriteLine("Config: Error - failed to save config to file: {0}", arg);
		}
		finally
		{
			streamWriter?.Close();
		}
	}
}
