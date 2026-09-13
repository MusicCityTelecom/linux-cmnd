using System;
using System.Runtime.CompilerServices;
using System.Runtime.ExceptionServices;
using System.Runtime.InteropServices;
using _003CCppImplementationDetails_003E;
using Dtapi;

namespace DTAPINET;

public class DtDevice : IDisposable
{
	private unsafe Dtapi.DtDevice* m_pDtDevice;

	protected internal unsafe void* m_pDtDeviceDerived;

	internal unsafe Dtapi.DtDevice* pDtDvc => m_pDtDevice;

	public unsafe DtHwFuncDesc[] m_rHwf
	{
		get
		{
			Dtapi.DtDevice* pDtDevice = m_pDtDevice;
			if (((int*)pDtDevice)[50] == 0)
			{
				return null;
			}
			int num = ((int*)pDtDevice)[15];
			DtHwFuncDesc[] array = new DtHwFuncDesc[num];
			int num2 = 0;
			if (0 < num)
			{
				int num3 = 0;
				do
				{
					array[num2] = new DtHwFuncDesc();
					array[num2].ConvertFromUnmgd((Dtapi.DtHwFuncDesc*)(((int*)m_pDtDevice)[50] + num3));
					num2++;
					num3 += 304;
				}
				while (num2 < ((int*)m_pDtDevice)[15]);
			}
			return array;
		}
	}

	public unsafe DtDeviceDesc m_DvcDesc
	{
		get
		{
			DtDeviceDesc dtDeviceDesc = new DtDeviceDesc();
			dtDeviceDesc.ConvertFromUnmgd((Dtapi.DtDeviceDesc*)((byte*)m_pDtDevice + 8));
			return dtDeviceDesc;
		}
	}

	public unsafe int Category()
	{
		Dtapi.DtDevice* pDtDevice = m_pDtDevice;
		return ((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, int>)(int)(*(uint*)(int)(*(uint*)pDtDevice)))((nint)pDtDevice);
	}

	public unsafe int ChanType(int Port)
	{
		Dtapi.DtDevice* pDtDevice = m_pDtDevice;
		return ((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, int, int>)(int)(*(uint*)(*(int*)pDtDevice + 4)))((nint)pDtDevice, Port);
	}

	public unsafe int FirmwareVersion()
	{
		Dtapi.DtDevice* pDtDevice = m_pDtDevice;
		return ((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, int>)(int)(*(uint*)(*(int*)pDtDevice + 8)))((nint)pDtDevice);
	}

	[return: MarshalAs(UnmanagedType.U1)]
	public unsafe bool IsAttached()
	{
		Dtapi.DtDevice* pDtDevice = m_pDtDevice;
		return ((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, byte>)(int)(*(uint*)(*(int*)pDtDevice + 20)))((nint)pDtDevice) != 0;
	}

	public unsafe int TypeNumber(int Port, DtCaps Caps)
	{
		Dtapi.DtDevice* pDtDevice = m_pDtDevice;
		return ((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, int, Dtapi.DtCaps, byte>)(int)(*(uint*)(*(int*)pDtDevice + 28)))((nint)pDtDevice, Port, *Caps.m_pCaps);
	}

	public unsafe int TypeNumber()
	{
		Dtapi.DtDevice* pDtDevice = m_pDtDevice;
		return ((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, int>)(int)(*(uint*)(*(int*)pDtDevice + 24)))((nint)pDtDevice);
	}

	public unsafe int FwPackageVersion()
	{
		Dtapi.DtDevice* pDtDevice = m_pDtDevice;
		return ((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, int>)(int)(*(uint*)(*(int*)pDtDevice + 12)))((nint)pDtDevice);
	}

	public unsafe DTAPI_RESULT AttachToIpAddr(byte[] Ip)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out _0024ArrayType_0024_0024_0024BY03E _0024ArrayType_0024_0024_0024BY03E);
		*(byte*)(&_0024ArrayType_0024_0024_0024BY03E) = Ip[0];
		System.Runtime.CompilerServices.Unsafe.As<_0024ArrayType_0024_0024_0024BY03E, byte>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref _0024ArrayType_0024_0024_0024BY03E, 1)) = Ip[1];
		System.Runtime.CompilerServices.Unsafe.As<_0024ArrayType_0024_0024_0024BY03E, byte>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref _0024ArrayType_0024_0024_0024BY03E, 2)) = Ip[2];
		System.Runtime.CompilerServices.Unsafe.As<_0024ArrayType_0024_0024_0024BY03E, byte>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref _0024ArrayType_0024_0024_0024BY03E, 3)) = Ip[3];
		Dtapi.DtDevice* pDtDevice = m_pDtDevice;
		return (DTAPI_RESULT)((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, byte*, uint>)(int)(*(uint*)(*(int*)pDtDevice + 32)))((nint)pDtDevice, (byte*)(&_0024ArrayType_0024_0024_0024BY03E));
	}

	public unsafe DTAPI_RESULT AttachToSerial(long SerialNumber)
	{
		Dtapi.DtDevice* pDtDevice = m_pDtDevice;
		return (DTAPI_RESULT)((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, long, uint>)(int)(*(uint*)(*(int*)pDtDevice + 36)))((nint)pDtDevice, SerialNumber);
	}

	public unsafe DTAPI_RESULT AttachToSlot(int PciBusNumber, int SlotNumber)
	{
		Dtapi.DtDevice* pDtDevice = m_pDtDevice;
		return (DTAPI_RESULT)((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, int, int, uint>)(int)(*(uint*)(*(int*)pDtDevice + 40)))((nint)pDtDevice, PciBusNumber, SlotNumber);
	}

	public unsafe DTAPI_RESULT AttachToType(int TypeNumber, int DeviceNo)
	{
		Dtapi.DtDevice* pDtDevice = m_pDtDevice;
		return (DTAPI_RESULT)((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, int, int, uint>)(int)(*(uint*)(*(int*)pDtDevice + 44)))((nint)pDtDevice, TypeNumber, DeviceNo);
	}

	public unsafe DTAPI_RESULT AttachToType(int TypeNumber)
	{
		Dtapi.DtDevice* pDtDevice = m_pDtDevice;
		return (DTAPI_RESULT)((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, int, int, uint>)(int)(*(uint*)(*(int*)pDtDevice + 44)))((nint)pDtDevice, TypeNumber, 0);
	}

	public unsafe DTAPI_RESULT ClearGpsErrors()
	{
		Dtapi.DtDevice* pDtDevice = m_pDtDevice;
		return (DTAPI_RESULT)((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, uint>)(int)(*(uint*)(*(int*)pDtDevice + 48)))((nint)pDtDevice);
	}

	public unsafe DTAPI_RESULT Detach()
	{
		Dtapi.DtDevice* pDtDevice = m_pDtDevice;
		return (DTAPI_RESULT)((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, uint>)(int)(*(uint*)(*(int*)pDtDevice + 52)))((nint)pDtDevice);
	}

	public unsafe DTAPI_RESULT FlashDisplay()
	{
		Dtapi.DtDevice* pDtDevice = m_pDtDevice;
		return (DTAPI_RESULT)((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, int, int, int, uint>)(int)(*(uint*)(*(int*)pDtDevice + 60)))((nint)pDtDevice, 5, 100, 100);
	}

	public unsafe DTAPI_RESULT FlashDisplay(int NumFlashes)
	{
		Dtapi.DtDevice* pDtDevice = m_pDtDevice;
		return (DTAPI_RESULT)((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, int, int, int, uint>)(int)(*(uint*)(*(int*)pDtDevice + 60)))((nint)pDtDevice, NumFlashes, 100, 100);
	}

	public unsafe DTAPI_RESULT FlashDisplay(int NumFlashes, int OnTime)
	{
		Dtapi.DtDevice* pDtDevice = m_pDtDevice;
		return (DTAPI_RESULT)((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, int, int, int, uint>)(int)(*(uint*)(*(int*)pDtDevice + 60)))((nint)pDtDevice, NumFlashes, OnTime, 100);
	}

	public unsafe DTAPI_RESULT FlashDisplay(int NumFlashes, int OnTime, int OffTime)
	{
		Dtapi.DtDevice* pDtDevice = m_pDtDevice;
		return (DTAPI_RESULT)((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, int, int, int, uint>)(int)(*(uint*)(*(int*)pDtDevice + 60)))((nint)pDtDevice, NumFlashes, OnTime, OffTime);
	}

	public unsafe DTAPI_RESULT GetAttribute(int Port, int AttrId, ref int AttrValue)
	{
		Dtapi.DtDevice* pDtDevice = m_pDtDevice;
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num);
		uint result = ((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, int, int, int*, uint>)(int)(*(uint*)(*(int*)pDtDevice + 68)))((nint)pDtDevice, Port, AttrId, &num);
		AttrValue = num;
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetAttribute(int AttrId, ref int AttrValue)
	{
		Dtapi.DtDevice* pDtDevice = m_pDtDevice;
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num);
		uint result = ((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, int, int*, uint>)(int)(*(uint*)(*(int*)pDtDevice + 72)))((nint)pDtDevice, AttrId, &num);
		AttrValue = num;
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetDescriptor(ref DtDeviceDesc rDvcDesc)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDeviceDesc dtDeviceDesc);
		System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtDeviceDesc, int>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtDeviceDesc, 72)) = 0;
		System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtDeviceDesc, int>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtDeviceDesc, 76)) = 0;
		System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtDeviceDesc, int>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtDeviceDesc, 80)) = 0;
		System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtDeviceDesc, int>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtDeviceDesc, 84)) = 0;
		System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtDeviceDesc, int>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtDeviceDesc, 88)) = 0;
		Dtapi.DtDevice* pDtDevice = m_pDtDevice;
		uint result = ((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, Dtapi.DtDeviceDesc*, uint>)(int)(*(uint*)(*(int*)pDtDevice + 76)))((nint)pDtDevice, &dtDeviceDesc);
		(rDvcDesc = new DtDeviceDesc()).ConvertFromUnmgd(&dtDeviceDesc);
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetDeviceDriverVersion(ref int DriverVersionMajor, ref int DriverVersionMinor, ref int DriverVersionBugFix, ref int DriverVersionBuild)
	{
		Dtapi.DtDevice* pDtDevice = m_pDtDevice;
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num);
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num2);
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num3);
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num4);
		uint result = ((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, int*, int*, int*, int*, uint>)(int)(*(uint*)(*(int*)pDtDevice + 80)))((nint)pDtDevice, &num, &num2, &num3, &num4);
		DriverVersionMajor = num;
		DriverVersionMinor = num2;
		DriverVersionBugFix = num3;
		DriverVersionBuild = num4;
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetDisplayName(ref string rName)
	{
		fixed (char* ptr = &(new char[64])[0])
		{
			Dtapi.DtDevice* pDtDevice = m_pDtDevice;
			uint num = ((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, char*, uint>)(int)(*(uint*)(*(int*)pDtDevice + 88)))((nint)pDtDevice, ptr);
			if (num == 0)
			{
				rName = new string(ptr);
			}
			else
			{
				rName = "";
			}
			return (DTAPI_RESULT)num;
		}
	}

	public unsafe DTAPI_RESULT GetFanSpeed(int Fan, ref int Rpm)
	{
		Dtapi.DtDevice* pDtDevice = m_pDtDevice;
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num);
		uint result = ((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, int, int*, uint>)(int)(*(uint*)(*(int*)pDtDevice + 100)))((nint)pDtDevice, Fan, &num);
		Rpm = num;
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetTemperature(int TempSens, ref int Temp)
	{
		Dtapi.DtDevice* pDtDevice = m_pDtDevice;
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num);
		uint result = ((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, int, int*, uint>)(int)(*(uint*)(*(int*)pDtDevice + 120)))((nint)pDtDevice, TempSens, &num);
		Temp = num;
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetFirmwareVersion(ref int FirmwareVersion)
	{
		Dtapi.DtDevice* pDtDevice = m_pDtDevice;
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num);
		uint result = ((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, int*, uint>)(int)(*(uint*)(*(int*)pDtDevice + 112)))((nint)pDtDevice, &num);
		FirmwareVersion = num;
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetGpsStatus(ref int Status, ref int Error)
	{
		Dtapi.DtDevice* pDtDevice = m_pDtDevice;
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num);
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num2);
		uint result = ((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, int*, int*, uint>)(int)(*(uint*)(*(int*)pDtDevice + 140)))((nint)pDtDevice, &num, &num2);
		Status = num;
		Error = num2;
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetGpsTime(ref int GpsTime)
	{
		Dtapi.DtDevice* pDtDevice = m_pDtDevice;
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num);
		uint result = ((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, int*, uint>)(int)(*(uint*)(*(int*)pDtDevice + 144)))((nint)pDtDevice, &num);
		GpsTime = num;
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetIoConfig(int Port, int Group, ref int Value, ref int SubValue, ref long ParXtra0, ref long ParXtra1)
	{
		Dtapi.DtDevice* pDtDevice = m_pDtDevice;
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num);
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num2);
		System.Runtime.CompilerServices.Unsafe.SkipInit(out long num3);
		System.Runtime.CompilerServices.Unsafe.SkipInit(out long num4);
		uint result = ((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, int, int, int*, int*, long*, long*, uint>)(int)(*(uint*)(*(int*)pDtDevice + 148)))((nint)pDtDevice, Port, Group, &num, &num2, &num3, &num4);
		Value = num;
		SubValue = num2;
		ParXtra0 = num3;
		ParXtra1 = num4;
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetIoConfig(int Port, int Group, ref int Value, ref int SubValue, ref long ParXtra0)
	{
		Dtapi.DtDevice* pDtDevice = m_pDtDevice;
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num);
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num2);
		System.Runtime.CompilerServices.Unsafe.SkipInit(out long num3);
		System.Runtime.CompilerServices.Unsafe.SkipInit(out long num4);
		uint result = ((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, int, int, int*, int*, long*, long*, uint>)(int)(*(uint*)(*(int*)pDtDevice + 148)))((nint)pDtDevice, Port, Group, &num, &num2, &num3, &num4);
		Value = num;
		SubValue = num2;
		ParXtra0 = num3;
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetIoConfig(int Port, int Group, ref int Value, ref int SubValue)
	{
		Dtapi.DtDevice* pDtDevice = m_pDtDevice;
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num);
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num2);
		System.Runtime.CompilerServices.Unsafe.SkipInit(out long num3);
		System.Runtime.CompilerServices.Unsafe.SkipInit(out long num4);
		uint result = ((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, int, int, int*, int*, long*, long*, uint>)(int)(*(uint*)(*(int*)pDtDevice + 148)))((nint)pDtDevice, Port, Group, &num, &num2, &num3, &num4);
		Value = num;
		SubValue = num2;
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetIoConfig(int Port, int Group, ref int Value)
	{
		Dtapi.DtDevice* pDtDevice = m_pDtDevice;
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num);
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num2);
		System.Runtime.CompilerServices.Unsafe.SkipInit(out long num3);
		System.Runtime.CompilerServices.Unsafe.SkipInit(out long num4);
		uint result = ((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, int, int, int*, int*, long*, long*, uint>)(int)(*(uint*)(*(int*)pDtDevice + 148)))((nint)pDtDevice, Port, Group, &num, &num2, &num3, &num4);
		Value = num;
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetIoConfig(DtIoConfig rIoConfig)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtIoConfig dtIoConfig);
		global::_003CModule_003E.Dtapi_002EDtIoConfig_002E_007Bctor_007D(&dtIoConfig);
		Dtapi.DtDevice* pDtDevice = m_pDtDevice;
		uint result = ((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, Dtapi.DtIoConfig*, uint>)(int)(*(uint*)(*(int*)pDtDevice + 164)))((nint)pDtDevice, &dtIoConfig);
		rIoConfig.ConvertFromUnmgd(&dtIoConfig);
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetNwSpeed(int Port, ref bool Enable, ref int Speed)
	{
		Dtapi.DtDevice* pDtDevice = m_pDtDevice;
		System.Runtime.CompilerServices.Unsafe.SkipInit(out bool flag);
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num);
		uint result = ((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, int, bool*, int*, uint>)(int)(*(uint*)(*(int*)pDtDevice + 176)))((nint)pDtDevice, Port, &flag, &num);
		Enable = flag;
		Speed = num;
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetRefClkCnt(ref ulong RefClkCnt, ref int RefClkFreqHz)
	{
		Dtapi.DtDevice* pDtDevice = m_pDtDevice;
		System.Runtime.CompilerServices.Unsafe.SkipInit(out ulong num);
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num2);
		uint result = ((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, ulong*, int*, uint>)(int)(*(uint*)(*(int*)pDtDevice + 184)))((nint)pDtDevice, &num, &num2);
		RefClkCnt = num;
		RefClkFreqHz = num2;
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetRefClkCnt(ref int RefClkCnt, ref int RefClkFreqHz)
	{
		Dtapi.DtDevice* pDtDevice = m_pDtDevice;
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num);
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num2);
		uint result = ((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, int*, int*, uint>)(int)(*(uint*)(*(int*)pDtDevice + 188)))((nint)pDtDevice, &num, &num2);
		RefClkCnt = num;
		RefClkFreqHz = num2;
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetRefClkCnt(ref ulong RefClkCnt)
	{
		Dtapi.DtDevice* pDtDevice = m_pDtDevice;
		System.Runtime.CompilerServices.Unsafe.SkipInit(out ulong num);
		uint result = ((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, ulong*, uint>)(int)(*(uint*)(*(int*)pDtDevice + 192)))((nint)pDtDevice, &num);
		RefClkCnt = num;
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetRefClkCnt(ref int RefClkCnt)
	{
		Dtapi.DtDevice* pDtDevice = m_pDtDevice;
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num);
		uint result = ((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, int*, uint>)(int)(*(uint*)(*(int*)pDtDevice + 196)))((nint)pDtDevice, &num);
		RefClkCnt = num;
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetRefClkFreq(ref int RefClkFreqHz)
	{
		Dtapi.DtDevice* pDtDevice = m_pDtDevice;
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num);
		uint result = ((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, int*, uint>)(int)(*(uint*)(*(int*)pDtDevice + 200)))((nint)pDtDevice, &num);
		RefClkFreqHz = num;
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetUsbSpeed(ref int UsbSpeed)
	{
		Dtapi.DtDevice* pDtDevice = m_pDtDevice;
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num);
		uint result = ((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, int*, uint>)(int)(*(uint*)(*(int*)pDtDevice + 224)))((nint)pDtDevice, &num);
		UsbSpeed = num;
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetVcxoState(ref bool Enable, ref int Lock, ref int VcxoClkFreqHz)
	{
		Dtapi.DtDevice* pDtDevice = m_pDtDevice;
		System.Runtime.CompilerServices.Unsafe.SkipInit(out bool flag);
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num);
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num2);
		uint result = ((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, bool*, int*, int*, uint>)(int)(*(uint*)(*(int*)pDtDevice + 228)))((nint)pDtDevice, &flag, &num, &num2);
		Enable = flag;
		Lock = num;
		VcxoClkFreqHz = num2;
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT HwFuncScan(int NumEntries, ref int NumEntriesResult, DtHwFuncDesc[] rHwFuncs)
	{
		Dtapi.DtHwFuncDesc* ptr = (Dtapi.DtHwFuncDesc*)global::_003CModule_003E.new_005B_005D(((uint)NumEntries > 14128181u) ? uint.MaxValue : ((uint)(NumEntries * 304)));
		Dtapi.DtHwFuncDesc* ptr3;
		try
		{
			if (ptr != null)
			{
				uint num = (uint)NumEntries;
				void* ptr2 = ptr;
				if (NumEntries != 0)
				{
					do
					{
						num--;
						global::_003CModule_003E.Dtapi_002EDtHwFuncDesc_002E_007Bctor_007D((Dtapi.DtHwFuncDesc*)ptr2);
						ptr2 = (byte*)ptr2 + 304;
					}
					while (num != 0);
				}
				ptr3 = ptr;
			}
			else
			{
				ptr3 = null;
			}
		}
		catch
		{
			//try-fault
			uint num2 = (uint)NumEntries;
			uint num3 = ((num2 > 14128181) ? uint.MaxValue : (num2 * 304));
			global::_003CModule_003E.delete_005B_005D(ptr, num3);
			throw;
		}
		Dtapi.DtDevice* pDtDevice = m_pDtDevice;
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num4);
		uint result = ((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, int, int*, Dtapi.DtHwFuncDesc*, uint>)(int)(*(uint*)(*(int*)pDtDevice + 232)))((nint)pDtDevice, NumEntries, &num4, ptr3);
		int num5 = Math.Min(NumEntries, NumEntriesResult);
		int num6 = 0;
		if (0 < num5)
		{
			Dtapi.DtHwFuncDesc* ptr4 = ptr3;
			do
			{
				rHwFuncs[num6].ConvertFromUnmgd(ptr4);
				num6++;
				ptr4 = (Dtapi.DtHwFuncDesc*)((byte*)ptr4 + 304);
			}
			while (num6 < num5);
		}
		global::_003CModule_003E.delete_005B_005D(ptr3);
		NumEntriesResult = num4;
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT LedControl(int LedControl)
	{
		Dtapi.DtDevice* pDtDevice = m_pDtDevice;
		return (DTAPI_RESULT)((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, int, uint>)(int)(*(uint*)(*(int*)pDtDevice + 240)))((nint)pDtDevice, LedControl);
	}

	public unsafe DTAPI_RESULT SetDisplayName(string rName)
	{
		char[] array = null;
		array = rName.ToCharArray();
		Array.Resize(ref array, (int)((nint)array.LongLength + 1));
		char[] array2 = array;
		array2[(nint)array2.LongLength - 1] = '\0';
		fixed (char* ptr = &array[0])
		{
			Dtapi.DtDevice* pDtDevice = m_pDtDevice;
			return (DTAPI_RESULT)((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, char*, uint>)(int)(*(uint*)(*(int*)pDtDevice + 252)))((nint)pDtDevice, ptr);
		}
	}

	public unsafe DTAPI_RESULT SetIoConfig(DtIoConfig[] rIoConfigs, int Count)
	{
		Dtapi.DtIoConfig* ptr = (Dtapi.DtIoConfig*)global::_003CModule_003E.new_005B_005D(((uint)Count > 134217727u) ? uint.MaxValue : ((uint)(Count * 32)));
		Dtapi.DtIoConfig* ptr3;
		try
		{
			if (ptr != null)
			{
				uint num = (uint)Count;
				void* ptr2 = ptr;
				if (Count != 0)
				{
					do
					{
						num--;
						global::_003CModule_003E.Dtapi_002EDtIoConfig_002E_007Bctor_007D((Dtapi.DtIoConfig*)ptr2);
						ptr2 = (byte*)ptr2 + 32;
					}
					while (num != 0);
				}
				ptr3 = ptr;
			}
			else
			{
				ptr3 = null;
			}
		}
		catch
		{
			//try-fault
			uint num2 = (uint)Count;
			uint num3 = ((num2 > 134217727) ? uint.MaxValue : (num2 * 32));
			global::_003CModule_003E.delete_005B_005D(ptr, num3);
			throw;
		}
		int num4 = 0;
		if (0 < Count)
		{
			Dtapi.DtIoConfig* ptr4 = ptr3;
			do
			{
				rIoConfigs[num4].ConvertToUnmgd(ptr4);
				num4++;
				ptr4 = (Dtapi.DtIoConfig*)((byte*)ptr4 + 32);
			}
			while (num4 < Count);
		}
		Dtapi.DtDevice* pDtDevice = m_pDtDevice;
		uint result = ((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, Dtapi.DtIoConfig*, int, uint>)(int)(*(uint*)(*(int*)pDtDevice + 264)))((nint)pDtDevice, ptr3, Count);
		global::_003CModule_003E.delete_005B_005D(ptr3);
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT SetIoConfig(int Port, int Group, int Value, int SubValue, long ParXtra0, long ParXtra1)
	{
		Dtapi.DtDevice* pDtDevice = m_pDtDevice;
		return (DTAPI_RESULT)((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, int, int, int, int, long, long, uint>)(int)(*(uint*)(*(int*)pDtDevice + 268)))((nint)pDtDevice, Port, Group, Value, SubValue, ParXtra0, ParXtra1);
	}

	public unsafe DTAPI_RESULT SetIoConfig(int Port, int Group, int Value, int SubValue, long ParXtra0)
	{
		Dtapi.DtDevice* pDtDevice = m_pDtDevice;
		return (DTAPI_RESULT)((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, int, int, int, int, long, long, uint>)(int)(*(uint*)(*(int*)pDtDevice + 268)))((nint)pDtDevice, Port, Group, Value, SubValue, ParXtra0, -1L);
	}

	public unsafe DTAPI_RESULT SetIoConfig(int Port, int Group, int Value, int SubValue)
	{
		Dtapi.DtDevice* pDtDevice = m_pDtDevice;
		return (DTAPI_RESULT)((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, int, int, int, int, long, long, uint>)(int)(*(uint*)(*(int*)pDtDevice + 268)))((nint)pDtDevice, Port, Group, Value, SubValue, -1L, -1L);
	}

	public unsafe DTAPI_RESULT SetIoConfig(int Port, int Group, int Value)
	{
		Dtapi.DtDevice* pDtDevice = m_pDtDevice;
		return (DTAPI_RESULT)((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, int, int, int, int, long, long, uint>)(int)(*(uint*)(*(int*)pDtDevice + 268)))((nint)pDtDevice, Port, Group, Value, -1, -1L, -1L);
	}

	public unsafe DTAPI_RESULT SetNwSpeed(int Port, [MarshalAs(UnmanagedType.U1)] bool Enable, int Speed)
	{
		Dtapi.DtDevice* pDtDevice = m_pDtDevice;
		return (DTAPI_RESULT)((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, int, byte, int, uint>)(int)(*(uint*)(*(int*)pDtDevice + 280)))((nint)pDtDevice, Port, Enable ? ((byte)1) : ((byte)0), Speed);
	}

	public unsafe DTAPI_RESULT VpdDelete(string rTag)
	{
		char[] array = null;
		array = rTag.ToCharArray();
		Array.Resize(ref array, (int)((nint)array.LongLength + 1));
		char[] array2 = array;
		array2[(nint)array2.LongLength - 1] = '\0';
		fixed (char* ptr = &array[0])
		{
			Dtapi.DtDevice* pDtDevice = m_pDtDevice;
			return (DTAPI_RESULT)((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, char*, uint>)(int)(*(uint*)(*(int*)pDtDevice + 300)))((nint)pDtDevice, ptr);
		}
	}

	public unsafe DTAPI_RESULT VpdRead(string rTag, ref byte[] rVpdItem)
	{
		char[] array = null;
		array = rTag.ToCharArray();
		Array.Resize(ref array, (int)((nint)array.LongLength + 1));
		char[] array2 = array;
		array2[(nint)array2.LongLength - 1] = '\0';
		fixed (char* ptr = &array[0])
		{
			byte[] array3 = new byte[63];
			fixed (byte* ptr2 = &array3[0])
			{
				int num = 0;
				Dtapi.DtDevice* pDtDevice = m_pDtDevice;
				uint num2 = ((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, char*, sbyte*, int*, uint>)(int)(*(uint*)(*(int*)pDtDevice + 308)))((nint)pDtDevice, ptr, (sbyte*)ptr2, &num);
				num = ((num2 == 0) ? num : 0);
				rVpdItem = array3;
				Array.Resize(ref rVpdItem, num);
				return (DTAPI_RESULT)num2;
			}
		}
	}

	public unsafe DTAPI_RESULT VpdRead(string rTag, ref string rVpdItem)
	{
		char[] array = null;
		array = rTag.ToCharArray();
		Array.Resize(ref array, (int)((nint)array.LongLength + 1));
		char[] array2 = array;
		array2[(nint)array2.LongLength - 1] = '\0';
		fixed (char* ptr = &array[0])
		{
			fixed (char* ptr2 = &(new char[64])[0])
			{
				Dtapi.DtDevice* pDtDevice = m_pDtDevice;
				uint num = ((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, char*, char*, uint>)(int)(*(uint*)(*(int*)pDtDevice + 316)))((nint)pDtDevice, ptr, ptr2);
				if (num == 0)
				{
					rVpdItem = new string(ptr2);
				}
				else
				{
					rVpdItem = "";
				}
				return (DTAPI_RESULT)num;
			}
		}
	}

	public unsafe DTAPI_RESULT VpdWrite(string rTag, byte[] rVpdItem)
	{
		char[] array = null;
		array = rTag.ToCharArray();
		Array.Resize(ref array, (int)((nint)array.LongLength + 1));
		char[] array2 = array;
		array2[(nint)array2.LongLength - 1] = '\0';
		fixed (char* ptr = &array[0])
		{
			fixed (byte* ptr2 = &rVpdItem[0])
			{
				Dtapi.DtDevice* pDtDevice = m_pDtDevice;
				return (DTAPI_RESULT)((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, char*, sbyte*, int, uint>)(int)(*(uint*)(*(int*)pDtDevice + 324)))((nint)pDtDevice, ptr, (sbyte*)ptr2, rVpdItem.Length);
			}
		}
	}

	public unsafe DTAPI_RESULT VpdWrite(string rTag, string rVpdItem)
	{
		char[] array = null;
		char[] array2 = null;
		array = rTag.ToCharArray();
		Array.Resize(ref array, (int)((nint)array.LongLength + 1));
		char[] array3 = array;
		array3[(nint)array3.LongLength - 1] = '\0';
		fixed (char* ptr = &array[0])
		{
			array2 = rVpdItem.ToCharArray();
			Array.Resize(ref array2, (int)((nint)array2.LongLength + 1));
			char[] array4 = array2;
			array4[(nint)array4.LongLength - 1] = '\0';
			fixed (char* ptr2 = &array2[0])
			{
				Dtapi.DtDevice* pDtDevice = m_pDtDevice;
				return (DTAPI_RESULT)((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, char*, char*, uint>)(int)(*(uint*)(*(int*)pDtDevice + 332)))((nint)pDtDevice, ptr, ptr2);
			}
		}
	}

	public unsafe DtDevice(void* pDtDeviceDerived)
	{
		m_pDtDevice = (Dtapi.DtDevice*)pDtDeviceDerived;
		m_pDtDeviceDerived = pDtDeviceDerived;
	}

	public unsafe DtDevice()
	{
		Dtapi.DtDevice* ptr = (Dtapi.DtDevice*)global::_003CModule_003E.@new(224u);
		Dtapi.DtDevice* pDtDevice;
		try
		{
			pDtDevice = ((ptr == null) ? null : global::_003CModule_003E.Dtapi_002EDtDevice_002E_007Bctor_007D(ptr));
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.delete(ptr, 224u);
			throw;
		}
		m_pDtDevice = pDtDevice;
		m_pDtDeviceDerived = null;
	}

	private void _007EDtDevice()
	{
		_0021DtDevice();
	}

	private unsafe void _0021DtDevice()
	{
		Dtapi.DtDevice* pDtDevice = m_pDtDevice;
		if (pDtDevice != null && m_pDtDeviceDerived == null)
		{
			Dtapi.DtDevice* ptr = pDtDevice;
			((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, uint, void*>)(int)(*(uint*)(*(int*)ptr + 344)))((nint)ptr, 1u);
			m_pDtDevice = null;
		}
	}

	[HandleProcessCorruptedStateExceptions]
	protected virtual void Dispose([MarshalAs(UnmanagedType.U1)] bool A_0)
	{
		if (A_0)
		{
			_0021DtDevice();
			return;
		}
		try
		{
			_0021DtDevice();
		}
		finally
		{
			base.Finalize();
		}
	}

	public virtual sealed void Dispose()
	{
		Dispose(A_0: true);
		GC.SuppressFinalize(this);
	}

	~DtDevice()
	{
		Dispose(A_0: false);
	}
}
