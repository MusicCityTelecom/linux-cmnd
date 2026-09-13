using System;
using System.IO;

namespace Philips.PSG.Gateway.Data;

public sealed class TsPlayer
{
	public enum TsPlayerState
	{
		NoTs,
		Paused,
		Running
	}

	private static readonly TsPlayer _instance = new TsPlayer();

	private TsPlayerState _state;

	private string _transportStreamFile;

	public static TsPlayer Instance => _instance;

	public TsPlayerState State
	{
		get
		{
			if (_state != TsPlayerState.Running)
			{
				_state = (IsMultiplexedTsExists() ? TsPlayerState.Paused : TsPlayerState.NoTs);
			}
			return _state;
		}
	}

	private TsPlayer()
	{
		Console.WriteLine("TSPlayer: initializing");
		_transportStreamFile = ConfigFactory.CurrentConfig.MultiplexedTsFile;
		SetInitialState();
	}

	public bool WasTsPlayInProgress()
	{
		return File.Exists(ConfigFactory.CurrentConfig.TsPlayFile);
	}

	public bool Play(PlayStreamHandlerErrored onErrorPlayStreamCallback)
	{
		if (_state != TsPlayerState.Paused)
		{
			return false;
		}
		if (string.IsNullOrEmpty(DekTecManager.Instance.DeviceType))
		{
			Console.WriteLine(Resources.DLG_DekTecDeviceNotAvailable.ToString() + " " + Resources.GLOBAL_AppName.ToString());
			return false;
		}
		Console.WriteLine("TSPlayer: play TS");
		SetState(TsPlayerState.Running);
		DekTecManager.Instance.StartPlayback(_transportStreamFile, onErrorPlayStreamCallback);
		return true;
	}

	public void Pause()
	{
		if (_state == TsPlayerState.Running)
		{
			Console.WriteLine("TSPlayer: pausing play");
			SetState(TsPlayerState.Paused);
			DekTecManager.Instance.StopPlayback();
		}
	}

	public bool IsValidTsFile(string filePath)
	{
		bool result = false;
		if (!string.IsNullOrEmpty(filePath) && File.Exists(filePath))
		{
			result = true;
		}
		return result;
	}

	private void SetState(TsPlayerState state)
	{
		if (_state != state)
		{
			_state = state;
			switch (state)
			{
			case TsPlayerState.Running:
				SetTsPlayInProgress(inProgress: true);
				break;
			case TsPlayerState.Paused:
				SetTsPlayInProgress(inProgress: false);
				break;
			}
		}
	}

	private void SetInitialState()
	{
		_state = (IsMultiplexedTsExists() ? TsPlayerState.Paused : TsPlayerState.NoTs);
	}

	private bool IsMultiplexedTsExists()
	{
		return File.Exists(_transportStreamFile);
	}

	private static void SetTsPlayInProgress(bool inProgress)
	{
		try
		{
			if (inProgress)
			{
				using (File.Create(ConfigFactory.CurrentConfig.TsPlayFile))
				{
					return;
				}
			}
			File.Delete(ConfigFactory.CurrentConfig.TsPlayFile);
		}
		catch (Exception ex)
		{
			Console.WriteLine("TSPlayer: Error - failed to create/delete TS play file: " + ex);
		}
	}
}
