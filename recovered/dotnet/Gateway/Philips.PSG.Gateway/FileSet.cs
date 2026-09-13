using System;
using System.Collections.Generic;
using System.IO;
using System.Xml.Serialization;

namespace Philips.PSG.Gateway;

[Serializable]
public class FileSet
{
	public enum FileSetRequirement
	{
		Optional,
		Required
	}

	public enum FileSetID
	{
		Undefined,
		EasyTransportStream,
		PrimeChannelTable,
		PrimeNVM,
		PrimeTheme,
		PrimeVSec,
		PrimeStandby,
		PrimeUpgrade,
		Prime2K10TransportStream,
		Easy2K10DsTransportStream,
		Easy2K10DlTransportStream,
		Prime2K11TransportStream,
		Media2K11TransportStream,
		Easy2K11Clone,
		Easy2K11Firmware
	}

	public enum FileSetUsage
	{
		UseFirstFile,
		UsePrefixFolder,
		UseInterimFolder
	}

	private static string DEFAULT_FOLDER = "";

	private string _prefixFolder;

	private List<string> _files;

	private FileSetRequirement _requirement;

	private FileSetID _ID;

	private string _interimFolder;

	private FileSetUsage _usage;

	private bool _includedInBuild;

	public string PrefixFolder
	{
		get
		{
			return _prefixFolder;
		}
		set
		{
			if (value == null)
			{
				_prefixFolder = DEFAULT_FOLDER;
			}
			else
			{
				_prefixFolder = value;
			}
		}
	}

	public string[] Files
	{
		get
		{
			return _files.ToArray();
		}
		set
		{
			_files.Clear();
			if (value != null)
			{
				_files.AddRange(value);
			}
		}
	}

	[XmlIgnore]
	public string[] PrefixedFiles
	{
		get
		{
			List<string> list = new List<string>(_files.Count);
			if (_prefixFolder.Equals(DEFAULT_FOLDER))
			{
				list.AddRange(_files);
			}
			else
			{
				foreach (string file in _files)
				{
					list.Add(_prefixFolder + "\\" + file);
				}
			}
			return list.ToArray();
		}
	}

	[XmlIgnore]
	public bool IsValid { get; internal set; }

	[XmlIgnore]
	public int NumberOfFiles => _files.Count;

	public FileSetRequirement Requirement
	{
		get
		{
			return _requirement;
		}
		set
		{
			_requirement = value;
		}
	}

	public FileSetID ID
	{
		get
		{
			return _ID;
		}
		set
		{
			_ID = value;
		}
	}

	public string InterimFolder
	{
		get
		{
			return _interimFolder;
		}
		set
		{
			if (value == null)
			{
				_interimFolder = DEFAULT_FOLDER;
			}
			else
			{
				_interimFolder = value;
			}
		}
	}

	public FileSetUsage Usage
	{
		get
		{
			return _usage;
		}
		set
		{
			_usage = value;
		}
	}

	[XmlIgnore]
	public bool IncludedInBuild
	{
		get
		{
			return _includedInBuild;
		}
		set
		{
			_includedInBuild = value;
		}
	}

	public FileSet()
	{
		_prefixFolder = DEFAULT_FOLDER;
		_files = new List<string>();
		_requirement = FileSetRequirement.Required;
		_ID = FileSetID.Undefined;
		_interimFolder = DEFAULT_FOLDER;
		_usage = FileSetUsage.UseFirstFile;
		_includedInBuild = false;
	}

	public string PrepareUsageArgument(string baseFolder)
	{
		string result = string.Empty;
		switch (_usage)
		{
		case FileSetUsage.UseFirstFile:
		{
			if (_files.Count < 1)
			{
				throw new InvalidDataException("UseFirstFile requires at least one file entry");
			}
			FileInfo[] files = new DirectoryInfo(baseFolder).GetFiles(PrefixedFiles[0]);
			if (files.Length == 1)
			{
				result = files[0].FullName;
			}
			break;
		}
		case FileSetUsage.UsePrefixFolder:
			if (_prefixFolder.Equals(DEFAULT_FOLDER))
			{
				throw new InvalidDataException("UsePrefixFolder requires a valid path");
			}
			result = baseFolder + "\\" + _prefixFolder;
			break;
		case FileSetUsage.UseInterimFolder:
		{
			if (_interimFolder.Equals(DEFAULT_FOLDER))
			{
				throw new InvalidDataException("UserInterimFolder requires a valid path");
			}
			string text = baseFolder + "\\" + _interimFolder;
			if (Directory.Exists(text))
			{
				throw new IOException($"Interim folder already exists: '{text}'");
			}
			Directory.CreateDirectory(text);
			if (!_prefixFolder.Equals(DEFAULT_FOLDER))
			{
				Directory.CreateDirectory(text + "\\" + _prefixFolder);
			}
			string[] prefixedFiles = PrefixedFiles;
			foreach (string text2 in prefixedFiles)
			{
				File.Copy(baseFolder + "\\" + text2, text + "\\" + text2);
			}
			result = text;
			break;
		}
		}
		return result;
	}

	public void ReleaseUsageArgument(string baseFolder)
	{
		switch (_usage)
		{
		case FileSetUsage.UseInterimFolder:
		{
			string path = baseFolder + "\\" + _interimFolder;
			if (Directory.Exists(path))
			{
				Directory.Delete(path, recursive: true);
			}
			break;
		}
		case FileSetUsage.UseFirstFile:
		case FileSetUsage.UsePrefixFolder:
			break;
		}
	}

	public TSGen.TS_FileVersionDetails GetVersionDetails()
	{
		TSGen.TS_FileVersionDetails result = default(TSGen.TS_FileVersionDetails);
		if (PrefixFolder != null)
		{
			string[] array = PrefixFolder.Split('\\');
			if (array.Length != 4)
			{
				return result;
			}
			result.hardwareModel = "0x" + array[0];
			result.hardwareVersion = "0x" + array[1];
			result.softwareModel = "0x" + array[2];
			result.softwareVersion = "0x" + array[3];
		}
		return result;
	}
}
