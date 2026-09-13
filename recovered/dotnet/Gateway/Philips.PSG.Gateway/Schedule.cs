using System;
using System.Collections.Generic;
using System.IO;
using System.Text;
using System.Xml.Serialization;

namespace Philips.PSG.Gateway;

[Serializable]
public class Schedule
{
	private List<ScheduleEvent> _events;

	public ScheduleEvent[] Events
	{
		get
		{
			return _events.ToArray();
		}
		set
		{
			_events.Clear();
			if (value != null)
			{
				_events.AddRange(value);
			}
		}
	}

	[XmlIgnore]
	public int NumberOfEvents => _events.Count;

	public Schedule()
	{
		_events = new List<ScheduleEvent>();
	}

	public void SaveToFile()
	{
		string scheduleDataFile = ConfigFactory.CurrentConfig.ScheduleDataFile;
		StreamWriter streamWriter = null;
		XmlSerializer xmlSerializer = new XmlSerializer(typeof(Schedule));
		try
		{
			Directory.CreateDirectory(Path.GetDirectoryName(scheduleDataFile));
			streamWriter = File.CreateText(scheduleDataFile);
			xmlSerializer.Serialize(streamWriter, this);
			streamWriter.Flush();
		}
		catch (Exception arg)
		{
			Console.WriteLine("Schedule: Error - failed to save schedule to file: {0}", arg);
		}
		finally
		{
			streamWriter?.Close();
		}
	}

	public static Schedule CreateFromFile()
	{
		StreamReader streamReader = null;
		Schedule result = null;
		string scheduleDataFile = ConfigFactory.CurrentConfig.ScheduleDataFile;
		XmlSerializer xmlSerializer = new XmlSerializer(typeof(Schedule));
		try
		{
			if (File.Exists(scheduleDataFile))
			{
				streamReader = File.OpenText(scheduleDataFile);
				result = (Schedule)xmlSerializer.Deserialize(streamReader);
			}
			else
			{
				result = new Schedule();
			}
		}
		catch (Exception arg)
		{
			Console.WriteLine("Schedule: Error - failed to create schedule from file: {0}", arg);
			result = new Schedule();
		}
		finally
		{
			streamReader?.Close();
		}
		return result;
	}

	public ScheduleEvent GetEventAtTime(TimeSpan time)
	{
		foreach (ScheduleEvent @event in _events)
		{
			if (@event.Enabled && time.CompareTo(@event.StartTime) >= 0 && time.CompareTo(@event.EndTime) < 0)
			{
				return @event;
			}
		}
		return null;
	}

	public ScheduleEvent GetCurrentEvent()
	{
		TimeSpan timeOfDay = DateTime.Now.TimeOfDay;
		return GetEventAtTime(timeOfDay);
	}

	public bool Validate()
	{
		List<string> list = new List<string>();
		foreach (ScheduleEvent @event in _events)
		{
			string transportStreamFile = @event.TransportStreamFile;
			if (!File.Exists(transportStreamFile) && !list.Contains(transportStreamFile))
			{
				list.Add(transportStreamFile);
			}
		}
		if (list.Count > 0)
		{
			ScheduleManager.Instance.Pause();
			StringBuilder stringBuilder = new StringBuilder(Resources.DLG_InvalidScheduleHeaderMessage);
			foreach (string item in list)
			{
				stringBuilder.AppendFormat("{0}\n", item);
			}
			stringBuilder.Append(Resources.DLG_InvalidScheduleFooterMessage);
			Console.WriteLine(stringBuilder.ToString() + " " + Resources.GLOBAL_AppName.ToString());
			return false;
		}
		return true;
	}
}
