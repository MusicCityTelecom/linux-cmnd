using System;
using System.Windows.Forms;

namespace Philips.PSG.Gateway;

public sealed class ScheduleManager
{
	public enum ScheduleState
	{
		NoScheduledEvents,
		Paused,
		Running
	}

	public delegate void ScheduleManagerScheduleChangedDelegate(Schedule schedule);

	public delegate void ScheduleManagerStateChangedDelegate(ScheduleState state);

	public delegate void ScheduleManagerCurrentEventChangedDelegate(ScheduleEvent currentEvent);

	private const int TIMER_TICK_INTERVAL_MS = 5000;

	private static readonly ScheduleManager _instance = new ScheduleManager();

	private Schedule _schedule;

	private ScheduleEvent _currentEvent;

	private ScheduleState _state;

	private Timer _timer;

	public static ScheduleManager Instance => _instance;

	public Schedule Schedule
	{
		get
		{
			return _schedule;
		}
		set
		{
			Pause(enableStateChange: false);
			if (value == null)
			{
				_schedule = new Schedule();
			}
			else
			{
				_schedule = value;
			}
			if (this.ScheduleChanged != null)
			{
				this.ScheduleChanged(_schedule);
			}
			if (_schedule.NumberOfEvents > 0)
			{
				SetState(ScheduleState.Paused);
			}
			else
			{
				SetState(ScheduleState.NoScheduledEvents);
			}
		}
	}

	public ScheduleState State => _state;

	public ScheduleEvent CurrentEvent => _currentEvent;

	public event ScheduleManagerScheduleChangedDelegate ScheduleChanged;

	public event ScheduleManagerStateChangedDelegate StateChanged;

	public event ScheduleManagerCurrentEventChangedDelegate CurrentEventChanged;

	private ScheduleManager()
	{
		this.ScheduleChanged = null;
		this.StateChanged = null;
		this.CurrentEventChanged = null;
		_timer = new Timer();
		_timer.Tick += Timer_Tick;
		_timer.Interval = 5000;
		_schedule = Schedule.CreateFromFile();
		_currentEvent = null;
		if (_schedule.NumberOfEvents > 0)
		{
			_state = ScheduleState.Paused;
		}
		else
		{
			_state = ScheduleState.NoScheduledEvents;
		}
	}

	private void SetState(ScheduleState state)
	{
		if (_state != state)
		{
			_state = state;
			if (this.StateChanged != null)
			{
				this.StateChanged(_state);
			}
		}
	}

	private void SetCurrentEvent(ScheduleEvent currentEvent)
	{
		if (_currentEvent != currentEvent)
		{
			ScheduleEvent currentEvent2 = _currentEvent;
			_currentEvent = currentEvent;
			if (this.CurrentEventChanged != null)
			{
				this.CurrentEventChanged(_currentEvent);
			}
			if (_currentEvent != null && currentEvent2 == null)
			{
				_currentEvent.StartPlayback();
			}
			else if (_currentEvent != null && _currentEvent != currentEvent2)
			{
				currentEvent2.StopPlayback();
				_currentEvent.StartPlayback();
			}
			else if (_currentEvent == null)
			{
				currentEvent2?.StopPlayback();
			}
		}
	}

	public void ReloadSchedule()
	{
		Schedule schedule = Schedule.CreateFromFile();
		Schedule = schedule;
	}

	public bool Run()
	{
		if (_state != ScheduleState.Paused || !_schedule.Validate())
		{
			return false;
		}
		if (string.IsNullOrEmpty(DekTecManager.Instance.DeviceType))
		{
			Console.WriteLine(Resources.DLG_DekTecDeviceNotAvailable.ToString() + " " + Resources.GLOBAL_AppName.ToString());
			return false;
		}
		SetState(ScheduleState.Running);
		CheckScheduledEvent();
		_timer.Start();
		return true;
	}

	private void Pause(bool enableStateChange)
	{
		if (_state == ScheduleState.Running)
		{
			_timer.Stop();
			HaltScheduledEvent();
			if (enableStateChange)
			{
				SetState(ScheduleState.Paused);
			}
		}
	}

	public void Pause()
	{
		Pause(enableStateChange: true);
	}

	private void CheckScheduledEvent()
	{
		if (_schedule.Validate())
		{
			ScheduleEvent currentEvent = _schedule.GetCurrentEvent();
			SetCurrentEvent(currentEvent);
		}
	}

	private void HaltScheduledEvent()
	{
		SetCurrentEvent(null);
	}

	private void Timer_Tick(object sender, EventArgs eventArgs)
	{
		if (_state == ScheduleState.Running)
		{
			CheckScheduledEvent();
		}
	}
}
