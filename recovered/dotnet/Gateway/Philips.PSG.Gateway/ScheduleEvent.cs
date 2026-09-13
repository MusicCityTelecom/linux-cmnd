using System;
using System.IO;
using System.Runtime.Serialization.Formatters.Binary;
using System.Xml.Serialization;

namespace Philips.PSG.Gateway;

[Serializable]
public class ScheduleEvent
{
	private static TimeSpan START_TIME_MIN = TimeSpan.Zero;

	private static TimeSpan START_TIME_MAX = new TimeSpan(24, 0, 0).Subtract(Settings.ScheduleTimeGranularity);

	private static TimeSpan END_TIME_MIN = Settings.ScheduleTimeGranularity;

	private static TimeSpan END_TIME_MAX = new TimeSpan(24, 0, 0);

	private string _transportStreamFile;

	private string _targetTvModel;

	private TimeSpan _startTime;

	private TimeSpan _endTime;

	private bool _enabled;

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

	[XmlIgnore]
	public TimeSpan StartTime
	{
		get
		{
			return _startTime;
		}
		set
		{
			_startTime = ClipTimeSpanValue(value, START_TIME_MIN, START_TIME_MAX);
			if (_startTime.CompareTo(_endTime) >= 0)
			{
				_endTime = _startTime.Add(Settings.ScheduleTimeGranularity);
			}
		}
	}

	[XmlElement("StartTime")]
	public string StartTimeAsString
	{
		get
		{
			return StartTime.ToString();
		}
		set
		{
			try
			{
				StartTime = TimeSpan.Parse(value);
			}
			catch (Exception)
			{
				StartTime = START_TIME_MIN;
			}
		}
	}

	[XmlIgnore]
	public TimeSpan EndTime
	{
		get
		{
			return _endTime;
		}
		set
		{
			_endTime = ClipTimeSpanValue(value, END_TIME_MIN, END_TIME_MAX);
			if (_endTime.CompareTo(_startTime) <= 0)
			{
				_startTime = _endTime.Subtract(Settings.ScheduleTimeGranularity);
			}
		}
	}

	[XmlElement("EndTime")]
	public string EndTimeAsString
	{
		get
		{
			return EndTime.ToString();
		}
		set
		{
			try
			{
				EndTime = TimeSpan.Parse(value);
			}
			catch (Exception)
			{
				EndTime = END_TIME_MIN;
			}
		}
	}

	public string TransportStreamFile
	{
		get
		{
			return _transportStreamFile;
		}
		set
		{
			if (value == null)
			{
				_transportStreamFile = string.Empty;
			}
			else
			{
				_transportStreamFile = value;
			}
		}
	}

	public bool Enabled
	{
		get
		{
			return _enabled;
		}
		set
		{
			_enabled = value;
		}
	}

	public ScheduleEvent()
	{
		_transportStreamFile = string.Empty;
		_targetTvModel = string.Empty;
		_startTime = START_TIME_MIN;
		_endTime = END_TIME_MIN;
		_enabled = true;
	}

	public ScheduleEvent Clone()
	{
		ScheduleEvent result = null;
		BinaryFormatter binaryFormatter = new BinaryFormatter();
		Stream stream = new MemoryStream();
		try
		{
			binaryFormatter.Serialize(stream, this);
			stream.Seek(0L, SeekOrigin.Begin);
			result = (ScheduleEvent)binaryFormatter.Deserialize(stream);
		}
		catch (Exception)
		{
			result = null;
		}
		finally
		{
			stream.Close();
		}
		return result;
	}

	private TimeSpan ClipTimeSpanValue(TimeSpan value, TimeSpan min, TimeSpan max)
	{
		if (value.CompareTo(min) < 0)
		{
			return min;
		}
		if (value.CompareTo(max) > 0)
		{
			return max;
		}
		return value;
	}

	public override string ToString()
	{
		return typeof(ScheduleEvent).Name + $"[TV Model={_targetTvModel}, Start={_startTime}, End={_endTime}, TS File={_transportStreamFile}]";
	}

	public void StartPlayback()
	{
		DekTecManager.Instance.StartPlayback(this);
	}

	public void StopPlayback()
	{
		DekTecManager.Instance.StopPlayback();
	}
}
