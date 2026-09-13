using System;
using System.IO;
using System.Threading;
using System.Windows.Threading;
using DTAPINET;

namespace Philips.PSG.Gateway;

public sealed class DekTecManager
{
	private delegate void DelegateUiThread();

	private PlayStreamHandlerErrored _onErrorPlayStreamCallback;

	private Dispatcher uiThread;

	private static readonly DekTecManager _instance = new DekTecManager();

	private const int SCAN_DEVICE_MAX = 20;

	private const int PLAYBACK_THREAD_EXIT_TIMEOUT = 20000;

	private const int PLAYBACK_FIFO_WAIT_PERIOD = 500;

	private const int PLAYBACK_TRANSFER_BUFFER_SIZE = 1048576;

	private const int PLAYBACK_MIN_FIFO_LOAD = 3145728;

	private bool _disposed;

	private string _attachedDeviceType;

	private DtDevice _device;

	private DtOutpChannel _outputChannel;

	private Thread _playbackThread;

	private bool _playbackThreadExit;

	private byte[] _transferBuffer;

	public static DekTecManager Instance => _instance;

	public string DeviceType => _attachedDeviceType;

	private DekTecManager()
	{
		uiThread = Dispatcher.CurrentDispatcher;
		_disposed = false;
		_attachedDeviceType = string.Empty;
		_playbackThread = null;
		_playbackThreadExit = false;
		_transferBuffer = new byte[1048576];
		_device = new DtDevice();
		_outputChannel = new DtOutpChannel();
		DtHwFuncDesc deviceFuncDesc = new DtHwFuncDesc();
		if (!ScanForSupportedDevice(ref deviceFuncDesc))
		{
			Console.WriteLine("DekTecManager: Error - device scan failed");
		}
		else if (!AttachDevice(deviceFuncDesc))
		{
			Console.WriteLine("DekTecManager: Error - device attach failed");
		}
		else
		{
			_attachedDeviceType = $"DTA-{deviceFuncDesc.m_DvcDesc.m_TypeNumber}";
		}
	}

	private bool ScanForSupportedDevice(ref DtHwFuncDesc deviceFuncDesc)
	{
		DtHwFuncDesc[] array = new DtHwFuncDesc[20];
		int NumEntriesResult = 0;
		Config currentConfig = ConfigFactory.CurrentConfig;
		try
		{
			if (DtGlobal.DtapiHwFuncScan(20, ref NumEntriesResult, array) != DTAPI_RESULT.OK)
			{
				return false;
			}
			for (int i = 0; i < NumEntriesResult; i++)
			{
				if ((array[i].m_Flags & currentConfig.DeviceCapabilityFlags) == currentConfig.DeviceCapabilityFlags)
				{
					deviceFuncDesc = array[i];
					return true;
				}
			}
		}
		catch (Exception)
		{
			return false;
		}
		return false;
	}

	private bool AttachDevice(DtHwFuncDesc deviceFuncDesc)
	{
		try
		{
			if (_device.AttachToSerial(deviceFuncDesc.m_DvcDesc.m_Serial) != DTAPI_RESULT.OK)
			{
				return false;
			}
			if ((deviceFuncDesc.m_ChanType & 2) != 2 && _device.SetIoConfig(deviceFuncDesc.m_Port, 0, 25) != DTAPI_RESULT.OK)
			{
				return false;
			}
			if (_outputChannel.AttachToPort(_device, deviceFuncDesc.m_Port) != DTAPI_RESULT.OK)
			{
				return false;
			}
		}
		catch (Exception)
		{
			return false;
		}
		return true;
	}

	public bool StartPlayback(ScheduleEvent playEvent)
	{
		if (_disposed)
		{
			return false;
		}
		if (!_device.IsAttached())
		{
			return false;
		}
		if (_playbackThread != null)
		{
			return false;
		}
		_playbackThread = new Thread(PBT_PlaybackHandler);
		_playbackThreadExit = false;
		_playbackThread.Start(playEvent);
		return true;
	}

	public bool StartPlayback(string tsFileName, PlayStreamHandlerErrored onErrorPlayStreamCallback)
	{
		_onErrorPlayStreamCallback = onErrorPlayStreamCallback;
		if (_disposed)
		{
			_onErrorPlayStreamCallback = null;
			return false;
		}
		if (!_device.IsAttached())
		{
			return false;
		}
		if (_playbackThread != null)
		{
			return false;
		}
		_playbackThread = new Thread(PBT_PlaybackHandler);
		_playbackThreadExit = false;
		_playbackThread.Start(tsFileName);
		return true;
	}

	public void StopPlayback()
	{
		if (!_disposed && _playbackThread != null)
		{
			_playbackThreadExit = true;
			if (_playbackThread.Join(20000))
			{
				_playbackThread = null;
			}
			_onErrorPlayStreamCallback = null;
		}
	}

	public void Dispose()
	{
		if (!_disposed)
		{
			StopPlayback();
			_disposed = true;
			try
			{
				_outputChannel.SetTxControl(1);
				_outputChannel.Detach(1);
				_device.Detach();
			}
			catch (Exception)
			{
			}
			_attachedDeviceType = string.Empty;
			_onErrorPlayStreamCallback = null;
		}
	}

	private void PBT_InitOutput()
	{
		Config currentConfig = ConfigFactory.CurrentConfig;
		DTAPI_RESULT dTAPI_RESULT = _outputChannel.Reset(1);
		if (dTAPI_RESULT != DTAPI_RESULT.OK)
		{
			throw new InvalidOperationException($"outputChannel.Reset() failed with {dTAPI_RESULT}");
		}
		dTAPI_RESULT = _outputChannel.SetTxControl(1);
		if (dTAPI_RESULT != DTAPI_RESULT.OK)
		{
			throw new InvalidOperationException($"outputChannel.SetTxControl() failed with {dTAPI_RESULT}");
		}
		dTAPI_RESULT = _outputChannel.SetTxMode(currentConfig.TxMode, currentConfig.StuffMode);
		if (dTAPI_RESULT != DTAPI_RESULT.OK)
		{
			throw new InvalidOperationException($"outputChannel.SetTxMode() failed with {dTAPI_RESULT}");
		}
		long rfControl = (long)currentConfig.ModulationFrequency * 100000L;
		dTAPI_RESULT = _outputChannel.SetRfControl(rfControl);
		if (dTAPI_RESULT != DTAPI_RESULT.OK)
		{
			throw new InvalidOperationException($"outputChannel.SetRfControl() failed with {dTAPI_RESULT}");
		}
		int parXtra = currentConfig.Bandwidth | currentConfig.Constellation | currentConfig.TransmissionMode | currentConfig.GuardInterval | 0x2000;
		dTAPI_RESULT = _outputChannel.SetModControl(currentConfig.ModulationType, currentConfig.CodeRate, parXtra, 0);
		if (dTAPI_RESULT != DTAPI_RESULT.OK)
		{
			throw new InvalidOperationException($"outputChannel.SetModControl() failed with {dTAPI_RESULT}");
		}
		DtHwFuncDesc rHwFuncDesc = new DtHwFuncDesc();
		if (_outputChannel.GetDescriptor(ref rHwFuncDesc) == DTAPI_RESULT.OK && (rHwFuncDesc.m_Flags & DTAPI.CAP_ADJLVL) == DTAPI.CAP_ADJLVL)
		{
			int outputLevel = (int)(currentConfig.OutputLevel * 10.0);
			dTAPI_RESULT = _outputChannel.SetOutputLevel(outputLevel);
			if (dTAPI_RESULT != DTAPI_RESULT.OK)
			{
				throw new InvalidOperationException($"outputChannel.SetOutputLevel() failed with {dTAPI_RESULT}");
			}
		}
		dTAPI_RESULT = _outputChannel.SetFifoSizeMax();
		if (dTAPI_RESULT != DTAPI_RESULT.OK)
		{
			throw new InvalidOperationException($"outputChannel.SetFifoSizeMax() failed with {dTAPI_RESULT}");
		}
		dTAPI_RESULT = _outputChannel.ClearFifo();
		if (dTAPI_RESULT != DTAPI_RESULT.OK)
		{
			throw new InvalidOperationException($"outputChannel.ClearFifo() failed with {dTAPI_RESULT}");
		}
	}

	private void PBT_LoopFile(string tsFileName)
	{
		FileStream fileStream = null;
		int FifoSize = 0;
		int FifoLoad = 0;
		bool flag = false;
		bool flag2 = false;
		DTAPI_RESULT dTAPI_RESULT = _outputChannel.SetTxControl(2);
		if (dTAPI_RESULT != DTAPI_RESULT.OK)
		{
			throw new InvalidOperationException($"outputChannel.SetTxControl() failed with {dTAPI_RESULT}");
		}
		dTAPI_RESULT = _outputChannel.GetFifoSize(ref FifoSize);
		if (dTAPI_RESULT != DTAPI_RESULT.OK)
		{
			throw new InvalidOperationException($"outputChannel.GetFifoSize() failed with {dTAPI_RESULT}");
		}
		try
		{
			fileStream = File.OpenRead(tsFileName);
			while (!_playbackThreadExit)
			{
				dTAPI_RESULT = _outputChannel.GetFifoLoad(ref FifoLoad);
				if (dTAPI_RESULT != DTAPI_RESULT.OK)
				{
					throw new InvalidOperationException($"outputChannel.GetFifoLoad() failed with {dTAPI_RESULT}");
				}
				if (FifoLoad + 1048576 >= FifoSize)
				{
					Thread.Sleep(500);
					continue;
				}
				int num = fileStream.Read(_transferBuffer, 0, 1048576);
				if (num == 0)
				{
					flag = true;
					num &= -4;
				}
				if (num > 0)
				{
					dTAPI_RESULT = _outputChannel.Write(_transferBuffer, num);
					if (dTAPI_RESULT != DTAPI_RESULT.OK)
					{
						throw new InvalidOperationException($"outputChannel.Write() failed with {dTAPI_RESULT}");
					}
				}
				dTAPI_RESULT = _outputChannel.GetFifoLoad(ref FifoLoad);
				if (dTAPI_RESULT != DTAPI_RESULT.OK)
				{
					throw new InvalidOperationException($"outputChannel.GetFifoLoad() failed with {dTAPI_RESULT}");
				}
				if (!flag2 && (flag || FifoLoad >= 3145728))
				{
					dTAPI_RESULT = _outputChannel.SetTxControl(3);
					if (dTAPI_RESULT != DTAPI_RESULT.OK)
					{
						throw new InvalidOperationException($"outputChannel.SetTxControl() failed with {dTAPI_RESULT}");
					}
					flag2 = true;
				}
				if (flag)
				{
					fileStream.Seek(0L, SeekOrigin.Begin);
					flag = false;
				}
			}
			dTAPI_RESULT = _outputChannel.ClearFifo();
			if (dTAPI_RESULT != DTAPI_RESULT.OK)
			{
				throw new InvalidOperationException($"outputChannel.ClearFifo() failed with {dTAPI_RESULT}");
			}
		}
		catch (Exception)
		{
			fileStream?.Close();
			throw;
		}
		fileStream.Close();
	}

	private void PBT_PlaybackHandler(object tsFile)
	{
		try
		{
			PBT_InitOutput();
			PBT_LoopFile(tsFile.ToString());
		}
		catch (Exception ex)
		{
			Exception exception = ex;
			if (_onErrorPlayStreamCallback != null && uiThread != null)
			{
				uiThread.BeginInvoke(DispatcherPriority.Background, (DelegateUiThread)delegate
				{
					_onErrorPlayStreamCallback(exception.Message, exception);
				});
			}
		}
	}
}
