using System;
using System.Collections.Generic;
using System.IO;
using System.Xml.Serialization;

namespace Philips.PSG.Gateway;

[Serializable]
public class Catalog
{
	private List<CatalogEntry> _entries;

	public CatalogEntry[] Entries
	{
		get
		{
			return _entries.ToArray();
		}
		set
		{
			_entries.Clear();
			if (value != null)
			{
				_entries.AddRange(value);
			}
		}
	}

	[XmlIgnore]
	public int NumberOfEntries => _entries.Count;

	public Catalog()
	{
		_entries = new List<CatalogEntry>();
	}

	public CatalogEntry[] GetValidCandidates(string rootFolder, StreamWriter logStream)
	{
		List<CatalogEntry> list = new List<CatalogEntry>();
		foreach (CatalogEntry entry in _entries)
		{
			if (entry.IsValidCandidate(rootFolder, logStream))
			{
				list.Add(entry);
			}
		}
		return list.ToArray();
	}

	public void SaveToFile()
	{
		string catalogFile = Settings.CatalogFile;
		StreamWriter streamWriter = null;
		XmlSerializer xmlSerializer = new XmlSerializer(typeof(Catalog));
		try
		{
			Directory.CreateDirectory(Path.GetDirectoryName(catalogFile));
			streamWriter = File.CreateText(catalogFile);
			xmlSerializer.Serialize(streamWriter, this);
			streamWriter.Flush();
		}
		catch (Exception)
		{
		}
		finally
		{
			streamWriter?.Close();
		}
	}

	public static Catalog CreateFromFile()
	{
		StreamReader streamReader = null;
		Catalog result = null;
		XmlSerializer xmlSerializer = new XmlSerializer(typeof(Catalog));
		try
		{
			streamReader = File.OpenText(Settings.CatalogFile);
			result = (Catalog)xmlSerializer.Deserialize(streamReader);
		}
		catch (Exception arg)
		{
			Console.WriteLine("Catalog: Error - failed to create catalog from file: {0}", arg);
			result = new Catalog();
		}
		finally
		{
			streamReader?.Close();
		}
		return result;
	}
}
