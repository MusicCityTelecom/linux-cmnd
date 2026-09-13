using System;
using System.Runtime.CompilerServices;
using System.Runtime.ExceptionServices;
using System.Runtime.InteropServices;
using Dtapi;
using std;

namespace DTAPINET;

public class DtOutpChannel : IDisposable
{
	internal unsafe Dtapi.DtOutpChannel* m_pDtOutpChannel;

	protected internal unsafe void* m_pDtOutpChannelDerived;

	public unsafe int Category()
	{
		return ((int*)m_pDtOutpChannel)[2];
	}

	public unsafe int FirmwareVersion()
	{
		return ((int*)m_pDtOutpChannel)[17];
	}

	[return: MarshalAs(UnmanagedType.U1)]
	public unsafe bool IsAttached()
	{
		return (byte)((((int*)m_pDtOutpChannel)[78] != 0) ? 1u : 0u) != 0;
	}

	public unsafe int TypeNumber(DtCaps Caps)
	{
		Dtapi.DtCaps pCaps = *Caps.m_pCaps;
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps);
		return global::_003CModule_003E.Dtapi_002EDtCaps_002E_003D_003D(global::_003CModule_003E.Dtapi_002EDtCaps_002E_0026((Dtapi.DtCaps*)((byte*)m_pDtOutpChannel + 208), &dtCaps, &pCaps), &pCaps) ? 1 : 0;
	}

	public unsafe int TypeNumber()
	{
		return ((int*)m_pDtOutpChannel)[9];
	}

	public unsafe DTAPI_RESULT AttachToPort(DtDevice rDtDvc, int Port, [MarshalAs(UnmanagedType.U1)] bool ProbeOnly)
	{
		Dtapi.DtOutpChannel* pDtOutpChannel = m_pDtOutpChannel;
		return (DTAPI_RESULT)((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, Dtapi.DtDevice*, int, byte, uint>)(int)(*(uint*)(*(int*)pDtOutpChannel + 4)))((nint)pDtOutpChannel, rDtDvc.pDtDvc, Port, ProbeOnly ? ((byte)1) : ((byte)0));
	}

	public unsafe DTAPI_RESULT AttachToPort(DtDevice rDtDvc, int Port)
	{
		Dtapi.DtOutpChannel* pDtOutpChannel = m_pDtOutpChannel;
		return (DTAPI_RESULT)((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, Dtapi.DtDevice*, int, byte, uint>)(int)(*(uint*)(*(int*)pDtOutpChannel + 4)))((nint)pDtOutpChannel, rDtDvc.pDtDvc, Port, 0);
	}

	public unsafe DTAPI_RESULT ClearFifo()
	{
		Dtapi.DtOutpChannel* pDtOutpChannel = m_pDtOutpChannel;
		return (DTAPI_RESULT)((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, uint>)(int)(*(uint*)(*(int*)pDtOutpChannel + 8)))((nint)pDtOutpChannel);
	}

	public unsafe DTAPI_RESULT ClearFlags(int Latched)
	{
		Dtapi.DtOutpChannel* pDtOutpChannel = m_pDtOutpChannel;
		return (DTAPI_RESULT)((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, int, uint>)(int)(*(uint*)(*(int*)pDtOutpChannel + 12)))((nint)pDtOutpChannel, Latched);
	}

	public unsafe DTAPI_RESULT ClearSfnErrors()
	{
		Dtapi.DtOutpChannel* pDtOutpChannel = m_pDtOutpChannel;
		return (DTAPI_RESULT)((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, uint>)(int)(*(uint*)(*(int*)pDtOutpChannel + 16)))((nint)pDtOutpChannel);
	}

	public unsafe DTAPI_RESULT Detach(int DetachMode)
	{
		Dtapi.DtOutpChannel* pDtOutpChannel = m_pDtOutpChannel;
		return (DTAPI_RESULT)((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, int, uint>)(int)(*(uint*)(*(int*)pDtOutpChannel + 20)))((nint)pDtOutpChannel, DetachMode);
	}

	public unsafe DTAPI_RESULT GetAttribute(int AttrId, ref int AttrValue)
	{
		Dtapi.DtOutpChannel* pDtOutpChannel = m_pDtOutpChannel;
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num);
		uint result = ((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, int, int*, uint>)(int)(*(uint*)(*(int*)pDtOutpChannel + 28)))((nint)pDtOutpChannel, AttrId, &num);
		AttrValue = num;
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetDescriptor(ref DtHwFuncDesc rHwFuncDesc)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtHwFuncDesc dtHwFuncDesc);
		global::_003CModule_003E.Dtapi_002EDtHwFuncDesc_002E_007Bctor_007D(&dtHwFuncDesc);
		Dtapi.DtOutpChannel* pDtOutpChannel = m_pDtOutpChannel;
		uint result = ((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, Dtapi.DtHwFuncDesc*, uint>)(int)(*(uint*)(*(int*)pDtOutpChannel + 32)))((nint)pDtOutpChannel, &dtHwFuncDesc);
		(rHwFuncDesc = new DtHwFuncDesc()).ConvertFromUnmgd(&dtHwFuncDesc);
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetExtClkFreq(ref int ExtClkFreq)
	{
		Dtapi.DtOutpChannel* pDtOutpChannel = m_pDtOutpChannel;
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num);
		uint result = ((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, int*, uint>)(int)(*(uint*)(*(int*)pDtOutpChannel + 36)))((nint)pDtOutpChannel, &num);
		ExtClkFreq = num;
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetFailsafeAlive(ref bool Alive)
	{
		Dtapi.DtOutpChannel* pDtOutpChannel = m_pDtOutpChannel;
		System.Runtime.CompilerServices.Unsafe.SkipInit(out bool flag);
		uint result = ((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, bool*, uint>)(int)(*(uint*)(*(int*)pDtOutpChannel + 40)))((nint)pDtOutpChannel, &flag);
		Alive = flag;
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetFailsafeConfig(ref bool Alive, ref int Timeout)
	{
		Dtapi.DtOutpChannel* pDtOutpChannel = m_pDtOutpChannel;
		System.Runtime.CompilerServices.Unsafe.SkipInit(out bool flag);
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num);
		uint result = ((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, bool*, int*, uint>)(int)(*(uint*)(*(int*)pDtOutpChannel + 44)))((nint)pDtOutpChannel, &flag, &num);
		Alive = flag;
		Timeout = num;
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetFifoLoad(ref int FifoLoad, int SubChan)
	{
		Dtapi.DtOutpChannel* pDtOutpChannel = m_pDtOutpChannel;
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num);
		uint result = ((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, int*, int, uint>)(int)(*(uint*)(*(int*)pDtOutpChannel + 48)))((nint)pDtOutpChannel, &num, SubChan);
		FifoLoad = num;
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetFifoLoad(ref int FifoLoad)
	{
		Dtapi.DtOutpChannel* pDtOutpChannel = m_pDtOutpChannel;
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num);
		uint result = ((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, int*, int, uint>)(int)(*(uint*)(*(int*)pDtOutpChannel + 48)))((nint)pDtOutpChannel, &num, 0);
		FifoLoad = num;
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetFifoSize(ref int FifoSize)
	{
		Dtapi.DtOutpChannel* pDtOutpChannel = m_pDtOutpChannel;
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num);
		uint result = ((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, int*, uint>)(int)(*(uint*)(*(int*)pDtOutpChannel + 52)))((nint)pDtOutpChannel, &num);
		FifoSize = num;
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetFifoSizeMax(ref int FifoSizeMax)
	{
		Dtapi.DtOutpChannel* pDtOutpChannel = m_pDtOutpChannel;
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num);
		uint result = ((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, int*, uint>)(int)(*(uint*)(*(int*)pDtOutpChannel + 56)))((nint)pDtOutpChannel, &num);
		FifoSizeMax = num;
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetFifoSizeTyp(ref int FifoSizeTyp)
	{
		Dtapi.DtOutpChannel* pDtOutpChannel = m_pDtOutpChannel;
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num);
		uint result = ((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, int*, uint>)(int)(*(uint*)(*(int*)pDtOutpChannel + 60)))((nint)pDtOutpChannel, &num);
		FifoSizeTyp = num;
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetFlags(ref int Flags, ref int Latched)
	{
		Dtapi.DtOutpChannel* pDtOutpChannel = m_pDtOutpChannel;
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num);
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num2);
		uint result = ((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, int*, int*, uint>)(int)(*(uint*)(*(int*)pDtOutpChannel + 64)))((nint)pDtOutpChannel, &num, &num2);
		Flags = num;
		Latched = num2;
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetIoConfig(int Group, ref int Value, ref int SubValue, ref long ParXtra0, ref long ParXtra1)
	{
		Dtapi.DtOutpChannel* pDtOutpChannel = m_pDtOutpChannel;
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num);
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num2);
		System.Runtime.CompilerServices.Unsafe.SkipInit(out long num3);
		System.Runtime.CompilerServices.Unsafe.SkipInit(out long num4);
		uint result = ((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, int, int*, int*, long*, long*, uint>)(int)(*(uint*)(*(int*)pDtOutpChannel + 68)))((nint)pDtOutpChannel, Group, &num, &num2, &num3, &num4);
		Value = num;
		SubValue = num2;
		ParXtra0 = num3;
		ParXtra1 = num4;
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetIoConfig(int Group, ref int Value, ref int SubValue, ref long ParXtra0)
	{
		Dtapi.DtOutpChannel* pDtOutpChannel = m_pDtOutpChannel;
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num);
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num2);
		System.Runtime.CompilerServices.Unsafe.SkipInit(out long num3);
		System.Runtime.CompilerServices.Unsafe.SkipInit(out long num4);
		uint result = ((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, int, int*, int*, long*, long*, uint>)(int)(*(uint*)(*(int*)pDtOutpChannel + 68)))((nint)pDtOutpChannel, Group, &num, &num2, &num3, &num4);
		Value = num;
		SubValue = num2;
		ParXtra0 = num3;
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetIoConfig(int Group, ref int Value, ref int SubValue)
	{
		Dtapi.DtOutpChannel* pDtOutpChannel = m_pDtOutpChannel;
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num);
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num2);
		System.Runtime.CompilerServices.Unsafe.SkipInit(out long num3);
		System.Runtime.CompilerServices.Unsafe.SkipInit(out long num4);
		uint result = ((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, int, int*, int*, long*, long*, uint>)(int)(*(uint*)(*(int*)pDtOutpChannel + 68)))((nint)pDtOutpChannel, Group, &num, &num2, &num3, &num4);
		Value = num;
		SubValue = num2;
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetIoConfig(int Group, ref int Value)
	{
		Dtapi.DtOutpChannel* pDtOutpChannel = m_pDtOutpChannel;
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num);
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num2);
		System.Runtime.CompilerServices.Unsafe.SkipInit(out long num3);
		System.Runtime.CompilerServices.Unsafe.SkipInit(out long num4);
		uint result = ((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, int, int*, int*, long*, long*, uint>)(int)(*(uint*)(*(int*)pDtOutpChannel + 68)))((nint)pDtOutpChannel, Group, &num, &num2, &num3, &num4);
		Value = num;
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetIpPars(ref DtIpPars rIpPars)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtIpPars dtIpPars);
		global::_003CModule_003E.Dtapi_002EDtIpPars_002E_007Bctor_007D(&dtIpPars);
		uint result;
		try
		{
			Dtapi.DtOutpChannel* pDtOutpChannel = m_pDtOutpChannel;
			result = ((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, Dtapi.DtIpPars*, uint>)(int)(*(uint*)(*(int*)pDtOutpChannel + 88)))((nint)pDtOutpChannel, &dtIpPars);
			(rIpPars = new DtIpPars()).ConvertFromUnmgd(&dtIpPars);
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtIpPars*, void>)(&global::_003CModule_003E.Dtapi_002EDtIpPars_002E_007Bdtor_007D), &dtIpPars);
			throw;
		}
		global::_003CModule_003E.Dtapi_002EDtIpPars_002E_007Bdtor_007D(&dtIpPars);
		return (DTAPI_RESULT)result;
	}

	public DTAPI_RESULT GetMaxFifoSize(ref int MaxFifoSize)
	{
		return GetFifoSizeMax(ref MaxFifoSize);
	}

	public unsafe DTAPI_RESULT GetModControl(ref int ModType, ref int ParXtra0, ref int ParXtra1, ref int ParXtra2)
	{
		Dtapi.DtOutpChannel* pDtOutpChannel = m_pDtOutpChannel;
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num);
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num2);
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num3);
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num4);
		System.Runtime.CompilerServices.Unsafe.SkipInit(out void* ptr);
		uint result = ((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, int*, int*, int*, int*, void**, uint>)(int)(*(uint*)(*(int*)pDtOutpChannel + 96)))((nint)pDtOutpChannel, &num, &num2, &num3, &num4, &ptr);
		ModType = num;
		ParXtra0 = num2;
		ParXtra1 = num3;
		ParXtra2 = num4;
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetOutputLevel(ref int LeveldBm)
	{
		Dtapi.DtOutpChannel* pDtOutpChannel = m_pDtOutpChannel;
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num);
		uint result = ((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, int*, uint>)(int)(*(uint*)(*(int*)pDtOutpChannel + 100)))((nint)pDtOutpChannel, &num);
		LeveldBm = num;
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetRfControl(ref double RfFreq, ref int LockStatus)
	{
		Dtapi.DtOutpChannel* pDtOutpChannel = m_pDtOutpChannel;
		System.Runtime.CompilerServices.Unsafe.SkipInit(out double num);
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num2);
		uint result = ((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, double*, int*, uint>)(int)(*(uint*)(*(int*)pDtOutpChannel + 104)))((nint)pDtOutpChannel, &num, &num2);
		RfFreq = num;
		LockStatus = num2;
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetRfControl(ref int RfFreq, ref int LockStatus)
	{
		Dtapi.DtOutpChannel* pDtOutpChannel = m_pDtOutpChannel;
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num);
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num2);
		uint result = ((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, int*, int*, uint>)(int)(*(uint*)(*(int*)pDtOutpChannel + 108)))((nint)pDtOutpChannel, &num, &num2);
		RfFreq = num;
		LockStatus = num2;
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetRfControl(ref long RfFreq, ref int LockStatus)
	{
		Dtapi.DtOutpChannel* pDtOutpChannel = m_pDtOutpChannel;
		System.Runtime.CompilerServices.Unsafe.SkipInit(out long num);
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num2);
		uint result = ((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, long*, int*, uint>)(int)(*(uint*)(*(int*)pDtOutpChannel + 112)))((nint)pDtOutpChannel, &num, &num2);
		RfFreq = num;
		LockStatus = num2;
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetSfnMaxTimeDiff(ref int TimeDiff)
	{
		Dtapi.DtOutpChannel* pDtOutpChannel = m_pDtOutpChannel;
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num);
		uint result = ((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, int*, uint>)(int)(*(uint*)(*(int*)pDtOutpChannel + 116)))((nint)pDtOutpChannel, &num);
		TimeDiff = num;
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetSfnModDelay(ref int ModDelay)
	{
		Dtapi.DtOutpChannel* pDtOutpChannel = m_pDtOutpChannel;
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num);
		uint result = ((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, int*, uint>)(int)(*(uint*)(*(int*)pDtOutpChannel + 124)))((nint)pDtOutpChannel, &num);
		ModDelay = num;
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetSfnStatus(ref int Status, ref int Error)
	{
		Dtapi.DtOutpChannel* pDtOutpChannel = m_pDtOutpChannel;
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num);
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num2);
		uint result = ((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, int*, int*, uint>)(int)(*(uint*)(*(int*)pDtOutpChannel + 128)))((nint)pDtOutpChannel, &num, &num2);
		Status = num;
		Error = num2;
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetSpiClk(ref int SpiClk)
	{
		Dtapi.DtOutpChannel* pDtOutpChannel = m_pDtOutpChannel;
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num);
		uint result = ((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, int*, uint>)(int)(*(uint*)(*(int*)pDtOutpChannel + 132)))((nint)pDtOutpChannel, &num);
		SpiClk = num;
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetTargetId(ref int Present, ref int TargetId)
	{
		Dtapi.DtOutpChannel* pDtOutpChannel = m_pDtOutpChannel;
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num);
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num2);
		uint result = ((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, int*, int*, uint>)(int)(*(uint*)(*(int*)pDtOutpChannel + 136)))((nint)pDtOutpChannel, &num, &num2);
		Present = num;
		TargetId = num2;
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetTsRateBps(ref DtFractionInt TsRate)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtFractionInt dtFractionInt);
		*(int*)(&dtFractionInt) = 0;
		System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtFractionInt, int>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtFractionInt, 4)) = 1;
		Dtapi.DtOutpChannel* pDtOutpChannel = m_pDtOutpChannel;
		uint result = ((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, Dtapi.DtFractionInt*, uint>)(int)(*(uint*)(*(int*)pDtOutpChannel + 140)))((nint)pDtOutpChannel, &dtFractionInt);
		System.Runtime.CompilerServices.Unsafe.SkipInit(out DtFractionInt dtFractionInt2);
		dtFractionInt2.m_Num = *(int*)(&dtFractionInt);
		dtFractionInt2.m_Den = System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtFractionInt, int>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtFractionInt, 4));
		TsRate = dtFractionInt2;
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetTsRateBps(ref int TsRate)
	{
		Dtapi.DtOutpChannel* pDtOutpChannel = m_pDtOutpChannel;
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num);
		uint result = ((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, int*, uint>)(int)(*(uint*)(*(int*)pDtOutpChannel + 144)))((nint)pDtOutpChannel, &num);
		TsRate = num;
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetTxControl(ref int TxControl)
	{
		Dtapi.DtOutpChannel* pDtOutpChannel = m_pDtOutpChannel;
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num);
		uint result = ((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, int*, uint>)(int)(*(uint*)(*(int*)pDtOutpChannel + 148)))((nint)pDtOutpChannel, &num);
		TxControl = num;
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetTxMode(ref int TxPacketMode, ref int TxStuffMode)
	{
		Dtapi.DtOutpChannel* pDtOutpChannel = m_pDtOutpChannel;
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num);
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num2);
		uint result = ((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, int*, int*, uint>)(int)(*(uint*)(*(int*)pDtOutpChannel + 152)))((nint)pDtOutpChannel, &num, &num2);
		TxPacketMode = num;
		TxStuffMode = num2;
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT LedControl(int LedControl)
	{
		Dtapi.DtOutpChannel* pDtOutpChannel = m_pDtOutpChannel;
		return (DTAPI_RESULT)((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, int, uint>)(int)(*(uint*)(*(int*)pDtOutpChannel + 156)))((nint)pDtOutpChannel, LedControl);
	}

	public unsafe DTAPI_RESULT Reset(int ResetMode)
	{
		Dtapi.DtOutpChannel* pDtOutpChannel = m_pDtOutpChannel;
		return (DTAPI_RESULT)((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, int, uint>)(int)(*(uint*)(*(int*)pDtOutpChannel + 160)))((nint)pDtOutpChannel, ResetMode);
	}

	public unsafe DTAPI_RESULT SetChannelModelling([MarshalAs(UnmanagedType.U1)] bool Enable, DtCmPars rCmPars)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCmPars dtCmPars);
		global::_003CModule_003E.Dtapi_002EDtCmPars_002E_007Bctor_007D(&dtCmPars);
		DTAPI_RESULT result;
		try
		{
			rCmPars?.ConvertToUnmgd(&dtCmPars);
			Dtapi.DtOutpChannel* pDtOutpChannel = m_pDtOutpChannel;
			result = (DTAPI_RESULT)((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, byte, Dtapi.DtCmPars*, uint>)(int)(*(uint*)(*(int*)pDtOutpChannel + 164)))((nint)pDtOutpChannel, Enable ? ((byte)1) : ((byte)0), &dtCmPars);
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtCmPars*, void>)(&global::_003CModule_003E.Dtapi_002EDtCmPars_002E_007Bdtor_007D), &dtCmPars);
			throw;
		}
		global::_003CModule_003E.std_002Evector_003CDtapi_003A_003ADtCmPath_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtCmPath_003E_0020_003E_002E_Tidy((vector_003CDtapi_003A_003ADtCmPath_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtCmPath_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtCmPars, 20)));
		return result;
	}

	public unsafe DTAPI_RESULT SetCustomRollOff([MarshalAs(UnmanagedType.U1)] bool Enable, DtFilterPars rFilter)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtFilterPars dtFilterPars);
		*(int*)(&dtFilterPars) = 0;
		System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtFilterPars, int>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtFilterPars, 4)) = 0;
		System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtFilterPars, int>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtFilterPars, 8)) = 0;
		DTAPI_RESULT result;
		try
		{
			rFilter?.ConvertToUnmgd(&dtFilterPars);
			Dtapi.DtOutpChannel* pDtOutpChannel = m_pDtOutpChannel;
			result = (DTAPI_RESULT)((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, byte, Dtapi.DtFilterPars*, uint>)(int)(*(uint*)(*(int*)pDtOutpChannel + 168)))((nint)pDtOutpChannel, Enable ? ((byte)1) : ((byte)0), &dtFilterPars);
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtFilterPars*, void>)(&global::_003CModule_003E.Dtapi_002EDtFilterPars_002E_007Bdtor_007D), &dtFilterPars);
			throw;
		}
		global::_003CModule_003E.std_002Evector_003CDtapi_003A_003ADtFiltCoeff_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtFiltCoeff_003E_0020_003E_002E_Tidy((vector_003CDtapi_003A_003ADtFiltCoeff_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtFiltCoeff_003E_0020_003E*)(&dtFilterPars));
		return result;
	}

	public unsafe DTAPI_RESULT SetFailsafeConfig([MarshalAs(UnmanagedType.U1)] bool Enable, int Timeout)
	{
		Dtapi.DtOutpChannel* pDtOutpChannel = m_pDtOutpChannel;
		return (DTAPI_RESULT)((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, byte, int, uint>)(int)(*(uint*)(*(int*)pDtOutpChannel + 176)))((nint)pDtOutpChannel, Enable ? ((byte)1) : ((byte)0), Timeout);
	}

	public unsafe DTAPI_RESULT SetFailsafeConfig([MarshalAs(UnmanagedType.U1)] bool Enable)
	{
		Dtapi.DtOutpChannel* pDtOutpChannel = m_pDtOutpChannel;
		return (DTAPI_RESULT)((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, byte, int, uint>)(int)(*(uint*)(*(int*)pDtOutpChannel + 176)))((nint)pDtOutpChannel, Enable ? ((byte)1) : ((byte)0), 0);
	}

	public unsafe DTAPI_RESULT SetFailsafeAlive()
	{
		Dtapi.DtOutpChannel* pDtOutpChannel = m_pDtOutpChannel;
		return (DTAPI_RESULT)((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, uint>)(int)(*(uint*)(*(int*)pDtOutpChannel + 172)))((nint)pDtOutpChannel);
	}

	public unsafe DTAPI_RESULT SetFifoSize(int FifoSize)
	{
		Dtapi.DtOutpChannel* pDtOutpChannel = m_pDtOutpChannel;
		return (DTAPI_RESULT)((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, int, uint>)(int)(*(uint*)(*(int*)pDtOutpChannel + 180)))((nint)pDtOutpChannel, FifoSize);
	}

	public unsafe DTAPI_RESULT SetFifoSizeMax()
	{
		Dtapi.DtOutpChannel* pDtOutpChannel = m_pDtOutpChannel;
		return (DTAPI_RESULT)((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, uint>)(int)(*(uint*)(*(int*)pDtOutpChannel + 184)))((nint)pDtOutpChannel);
	}

	public unsafe DTAPI_RESULT SetFifoSizeTyp()
	{
		Dtapi.DtOutpChannel* pDtOutpChannel = m_pDtOutpChannel;
		return (DTAPI_RESULT)((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, uint>)(int)(*(uint*)(*(int*)pDtOutpChannel + 188)))((nint)pDtOutpChannel);
	}

	public unsafe DTAPI_RESULT SetIoConfig(int Group, int Value, int SubValue, long ParXtra0)
	{
		Dtapi.DtOutpChannel* pDtOutpChannel = m_pDtOutpChannel;
		return (DTAPI_RESULT)((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, int, int, int, long, long, uint>)(int)(*(uint*)(*(int*)pDtOutpChannel + 192)))((nint)pDtOutpChannel, Group, Value, SubValue, ParXtra0, -1L);
	}

	public unsafe DTAPI_RESULT SetIoConfig(int Group, int Value, int SubValue)
	{
		Dtapi.DtOutpChannel* pDtOutpChannel = m_pDtOutpChannel;
		return (DTAPI_RESULT)((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, int, int, int, long, long, uint>)(int)(*(uint*)(*(int*)pDtOutpChannel + 192)))((nint)pDtOutpChannel, Group, Value, SubValue, -1L, -1L);
	}

	public unsafe DTAPI_RESULT SetIoConfig(int Group, int Value)
	{
		Dtapi.DtOutpChannel* pDtOutpChannel = m_pDtOutpChannel;
		return (DTAPI_RESULT)((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, int, int, int, long, long, uint>)(int)(*(uint*)(*(int*)pDtOutpChannel + 192)))((nint)pDtOutpChannel, Group, Value, -1, -1L, -1L);
	}

	public unsafe DTAPI_RESULT SetIoConfig(int Group, int Value, int SubValue, long ParXtra0, long ParXtra1)
	{
		Dtapi.DtOutpChannel* pDtOutpChannel = m_pDtOutpChannel;
		return (DTAPI_RESULT)((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, int, int, int, long, long, uint>)(int)(*(uint*)(*(int*)pDtOutpChannel + 192)))((nint)pDtOutpChannel, Group, Value, SubValue, ParXtra0, ParXtra1);
	}

	public unsafe DTAPI_RESULT SetIpPars(DtIpPars rIpPars)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtIpPars dtIpPars);
		global::_003CModule_003E.Dtapi_002EDtIpPars_002E_007Bctor_007D(&dtIpPars);
		DTAPI_RESULT result;
		try
		{
			rIpPars.ConvertToUnmgd(&dtIpPars);
			Dtapi.DtOutpChannel* pDtOutpChannel = m_pDtOutpChannel;
			result = (DTAPI_RESULT)((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, Dtapi.DtIpPars*, uint>)(int)(*(uint*)(*(int*)pDtOutpChannel + 200)))((nint)pDtOutpChannel, &dtIpPars);
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtIpPars*, void>)(&global::_003CModule_003E.Dtapi_002EDtIpPars_002E_007Bdtor_007D), &dtIpPars);
			throw;
		}
		global::_003CModule_003E.Dtapi_002EDtIpPars_002E_007Bdtor_007D(&dtIpPars);
		return result;
	}

	public unsafe DTAPI_RESULT SetModControl(int ModType, int ParXtra0, int ParXtra1, int ParXtra2)
	{
		Dtapi.DtOutpChannel* pDtOutpChannel = m_pDtOutpChannel;
		return (DTAPI_RESULT)((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, int, int, int, int, uint>)(int)(*(uint*)(*(int*)pDtOutpChannel + 212)))((nint)pDtOutpChannel, ModType, ParXtra0, ParXtra1, ParXtra2);
	}

	public unsafe DTAPI_RESULT SetModControl(ValueType rIqPars)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtIqDirectPars dtIqDirectPars);
		global::_003CModule_003E.Dtapi_002EDtIqDirectPars_002E_007Bctor_007D(&dtIqDirectPars);
		((DtIqDirectPars)rIqPars).ConvertToUnmgd(&dtIqDirectPars);
		Dtapi.DtOutpChannel* pDtOutpChannel = m_pDtOutpChannel;
		return (DTAPI_RESULT)((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, Dtapi.DtIqDirectPars*, uint>)(int)(*(uint*)(*(int*)pDtOutpChannel + 232)))((nint)pDtOutpChannel, &dtIqDirectPars);
	}

	public unsafe DTAPI_RESULT SetModControl(DtDvbT2Pars rT2Pars)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDvbT2Pars dtDvbT2Pars);
		global::_003CModule_003E.Dtapi_002EDtDvbT2Pars_002E_007Bctor_007D(&dtDvbT2Pars);
		DTAPI_RESULT result;
		try
		{
			rT2Pars.ConvertToUnmgd(&dtDvbT2Pars);
			Dtapi.DtOutpChannel* pDtOutpChannel = m_pDtOutpChannel;
			result = (DTAPI_RESULT)((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, Dtapi.DtDvbT2Pars*, uint>)(int)(*(uint*)(*(int*)pDtOutpChannel + 236)))((nint)pDtOutpChannel, &dtDvbT2Pars);
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtDvbT2Pars*, void>)(&global::_003CModule_003E.Dtapi_002EDtDvbT2Pars_002E_007Bdtor_007D), &dtDvbT2Pars);
			throw;
		}
		global::_003CModule_003E.Dtapi_002EDtDvbT2Pars_002E_007Bdtor_007D(&dtDvbT2Pars);
		return result;
	}

	public unsafe DTAPI_RESULT SetModControl(DtDvbC2Pars rC2Pars)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDvbC2Pars dtDvbC2Pars);
		global::_003CModule_003E.Dtapi_002EDtDvbC2Pars_002E_007Bctor_007D(&dtDvbC2Pars);
		DTAPI_RESULT result;
		try
		{
			rC2Pars.ConvertToUnmgd(&dtDvbC2Pars);
			Dtapi.DtOutpChannel* pDtOutpChannel = m_pDtOutpChannel;
			result = (DTAPI_RESULT)((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, Dtapi.DtDvbC2Pars*, uint>)(int)(*(uint*)(*(int*)pDtOutpChannel + 256)))((nint)pDtOutpChannel, &dtDvbC2Pars);
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtDvbC2Pars*, void>)(&global::_003CModule_003E.Dtapi_002EDtDvbC2Pars_002E_007Bdtor_007D), &dtDvbC2Pars);
			throw;
		}
		global::_003CModule_003E.Dtapi_002EDtDvbC2Pars_002E_007Bdtor_007D(&dtDvbC2Pars);
		return result;
	}

	public unsafe DTAPI_RESULT SetModControl(DtDvbCidPars rCidPars)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDvbCidPars dtDvbCidPars);
		global::_003CModule_003E.Dtapi_002EDtDvbCidPars_002E_007Bctor_007D(&dtDvbCidPars);
		DTAPI_RESULT result;
		try
		{
			rCidPars.ConvertToUnmgd(&dtDvbCidPars);
			Dtapi.DtOutpChannel* pDtOutpChannel = m_pDtOutpChannel;
			result = (DTAPI_RESULT)((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, Dtapi.DtDvbCidPars*, uint>)(int)(*(uint*)(*(int*)pDtOutpChannel + 252)))((nint)pDtOutpChannel, &dtDvbCidPars);
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtDvbCidPars*, void>)(&global::_003CModule_003E.Dtapi_002EDtDvbCidPars_002E_007Bdtor_007D), &dtDvbCidPars);
			throw;
		}
		map_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_0020_003E* pThis = (map_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtDvbCidPars, 12));
		try
		{
			global::_003CModule_003E.std_002E_Tree_003Cstd_003A_003A_Tmap_traits_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_002C0_003E_0020_003E_002E_Tidy((_Tree_003Cstd_003A_003A_Tmap_traits_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_002C0_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtDvbCidPars, 12)));
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<_Tree_comp_alloc_003Cstd_003A_003A_Tmap_traits_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_002C0_003E_0020_003E*, void>)(&global::_003CModule_003E.std_002E_Tree_comp_alloc_003Cstd_003A_003A_Tmap_traits_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_002C0_003E_0020_003E_002E_007Bdtor_007D), pThis);
			throw;
		}
		global::_003CModule_003E.std_002E_Tree_node_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_002Cvoid_0020_002A_003E_002E_Freenode0_003Cclass_0020std_003A_003Aallocator_003Cstruct_0020std_003A_003A_Tree_node_003Cstruct_0020std_003A_003Apair_003Cint_0020const_0020_002Cint_003E_002Cvoid_0020_002A_003E_0020_003E_0020_003E((allocator_003Cstd_003A_003A_Tree_node_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_002Cvoid_0020_002A_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtDvbCidPars, 12)), (_Tree_node_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_002Cvoid_0020_002A_003E*)(int)System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtDvbCidPars, uint>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtDvbCidPars, 12)));
		return result;
	}

	public unsafe DTAPI_RESULT SetModControl(DtCmmbPars rCmmbPars)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCmmbPars dtCmmbPars);
		global::_003CModule_003E.Dtapi_002EDtCmmbPars_002E_007Bctor_007D(&dtCmmbPars);
		rCmmbPars.ConvertToUnmgd(&dtCmmbPars);
		Dtapi.DtOutpChannel* pDtOutpChannel = m_pDtOutpChannel;
		return (DTAPI_RESULT)((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, Dtapi.DtCmmbPars*, uint>)(int)(*(uint*)(*(int*)pDtOutpChannel + 264)))((nint)pDtOutpChannel, &dtCmmbPars);
	}

	public unsafe DTAPI_RESULT SetModControl(DtAtsc3Pars rA3Pars)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtAtsc3Pars dtAtsc3Pars);
		global::_003CModule_003E.Dtapi_002EDtAtsc3Pars_002E_007Bctor_007D(&dtAtsc3Pars);
		DTAPI_RESULT result;
		try
		{
			rA3Pars.ConvertToUnmgd(&dtAtsc3Pars);
			Dtapi.DtOutpChannel* pDtOutpChannel = m_pDtOutpChannel;
			result = (DTAPI_RESULT)((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, Dtapi.DtAtsc3Pars*, uint>)(int)(*(uint*)(*(int*)pDtOutpChannel + 272)))((nint)pDtOutpChannel, &dtAtsc3Pars);
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtAtsc3Pars*, void>)(&global::_003CModule_003E.Dtapi_002EDtAtsc3Pars_002E_007Bdtor_007D), &dtAtsc3Pars);
			throw;
		}
		try
		{
			global::_003CModule_003E.__ehvec_dtor(System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtAtsc3Pars, 132)), 216u, 64u, (delegate*<void*, void>)(delegate*<Dtapi.DtPlpInpPars*, void>)(&global::_003CModule_003E.Dtapi_002EDtPlpInpPars_002E_007Bdtor_007D));
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<vector_003CDtapi_003A_003ADtAtsc3SubframePars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3SubframePars_003E_0020_003E*, void>)(&global::_003CModule_003E.std_002Evector_003CDtapi_003A_003ADtAtsc3SubframePars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3SubframePars_003E_0020_003E_002E_007Bdtor_007D), System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtAtsc3Pars, 116)));
			throw;
		}
		global::_003CModule_003E.std_002Evector_003CDtapi_003A_003ADtAtsc3SubframePars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3SubframePars_003E_0020_003E_002E_Tidy((vector_003CDtapi_003A_003ADtAtsc3SubframePars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3SubframePars_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtAtsc3Pars, 116)));
		return result;
	}

	public unsafe DTAPI_RESULT SetModControl(DtIsdbTmmPars rIsdbTmmPars)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtIsdbTmmPars dtIsdbTmmPars);
		global::_003CModule_003E.Dtapi_002EDtIsdbTmmPars_002E_007Bctor_007D(&dtIsdbTmmPars);
		DTAPI_RESULT result;
		try
		{
			rIsdbTmmPars.ConvertToUnmgd(&dtIsdbTmmPars);
			Dtapi.DtOutpChannel* pDtOutpChannel = m_pDtOutpChannel;
			result = (DTAPI_RESULT)((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, Dtapi.DtIsdbTmmPars*, uint>)(int)(*(uint*)(*(int*)pDtOutpChannel + 216)))((nint)pDtOutpChannel, &dtIsdbTmmPars);
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtIsdbTmmPars*, void>)(&global::_003CModule_003E.Dtapi_002EDtIsdbTmmPars_002E_007Bdtor_007D), &dtIsdbTmmPars);
			throw;
		}
		global::_003CModule_003E.Dtapi_002EDtIsdbTmmPars_002E_007Bdtor_007D(&dtIsdbTmmPars);
		return result;
	}

	public unsafe DTAPI_RESULT SetModControl(DtIsdbtPars rIsdbtPars)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtIsdbtPars dtIsdbtPars);
		global::_003CModule_003E.Dtapi_002EDtIsdbtPars_002E_007Bctor_007D(&dtIsdbtPars);
		DTAPI_RESULT result;
		try
		{
			rIsdbtPars.ConvertToUnmgd(&dtIsdbtPars);
			Dtapi.DtOutpChannel* pDtOutpChannel = m_pDtOutpChannel;
			result = (DTAPI_RESULT)((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, Dtapi.DtIsdbtPars*, uint>)(int)(*(uint*)(*(int*)pDtOutpChannel + 220)))((nint)pDtOutpChannel, &dtIsdbtPars);
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtIsdbtPars*, void>)(&global::_003CModule_003E.Dtapi_002EDtIsdbtPars_002E_007Bdtor_007D), &dtIsdbtPars);
			throw;
		}
		map_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_0020_003E* pThis = (map_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtIsdbtPars, 100));
		try
		{
			global::_003CModule_003E.std_002E_Tree_003Cstd_003A_003A_Tmap_traits_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_002C0_003E_0020_003E_002E_Tidy((_Tree_003Cstd_003A_003A_Tmap_traits_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_002C0_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtIsdbtPars, 100)));
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<_Tree_comp_alloc_003Cstd_003A_003A_Tmap_traits_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_002C0_003E_0020_003E*, void>)(&global::_003CModule_003E.std_002E_Tree_comp_alloc_003Cstd_003A_003A_Tmap_traits_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_002C0_003E_0020_003E_002E_007Bdtor_007D), pThis);
			throw;
		}
		global::_003CModule_003E.std_002E_Tree_node_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_002Cvoid_0020_002A_003E_002E_Freenode0_003Cclass_0020std_003A_003Aallocator_003Cstruct_0020std_003A_003A_Tree_node_003Cstruct_0020std_003A_003Apair_003Cint_0020const_0020_002Cint_003E_002Cvoid_0020_002A_003E_0020_003E_0020_003E((allocator_003Cstd_003A_003A_Tree_node_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_002Cvoid_0020_002A_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtIsdbtPars, 100)), (_Tree_node_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_002Cvoid_0020_002A_003E*)(int)System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtIsdbtPars, uint>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtIsdbtPars, 100)));
		return result;
	}

	public unsafe DTAPI_RESULT SetModControl(DtIsdbS3Pars rIsdbS3Pars)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtIsdbS3Pars dtIsdbS3Pars);
		*(int*)(&dtIsdbS3Pars) = rIsdbS3Pars.m_SymRate;
		Dtapi.DtOutpChannel* pDtOutpChannel = m_pDtOutpChannel;
		return (DTAPI_RESULT)((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, Dtapi.DtIsdbS3Pars*, uint>)(int)(*(uint*)(*(int*)pDtOutpChannel + 224)))((nint)pDtOutpChannel, &dtIsdbS3Pars);
	}

	public unsafe DTAPI_RESULT SetModControl(DtIsdbsPars rIsdbsPars)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtIsdbsPars dtIsdbsPars);
		*(sbyte*)(&dtIsdbsPars) = 0;
		System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtIsdbsPars, sbyte>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtIsdbsPars, 1)) = 0;
		rIsdbsPars.ConvertToUnmgd(&dtIsdbsPars);
		Dtapi.DtOutpChannel* pDtOutpChannel = m_pDtOutpChannel;
		return (DTAPI_RESULT)((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, Dtapi.DtIsdbsPars*, uint>)(int)(*(uint*)(*(int*)pDtOutpChannel + 228)))((nint)pDtOutpChannel, &dtIsdbsPars);
	}

	public unsafe DTAPI_RESULT SetMultiModConfig(int NumSubChan, int FreqSpacing)
	{
		Dtapi.DtOutpChannel* pDtOutpChannel = m_pDtOutpChannel;
		return (DTAPI_RESULT)((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, int, int, uint>)(int)(*(uint*)(*(int*)pDtOutpChannel + 276)))((nint)pDtOutpChannel, NumSubChan, FreqSpacing);
	}

	public unsafe DTAPI_RESULT SetOutputLevel(int LeveldBm)
	{
		Dtapi.DtOutpChannel* pDtOutpChannel = m_pDtOutpChannel;
		return (DTAPI_RESULT)((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, int, uint>)(int)(*(uint*)(*(int*)pDtOutpChannel + 280)))((nint)pDtOutpChannel, LeveldBm);
	}

	public unsafe DTAPI_RESULT SetPower(int Power)
	{
		Dtapi.DtOutpChannel* pDtOutpChannel = m_pDtOutpChannel;
		return (DTAPI_RESULT)((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, int, uint>)(int)(*(uint*)(*(int*)pDtOutpChannel + 288)))((nint)pDtOutpChannel, Power);
	}

	public unsafe DTAPI_RESULT SetRfControl(double RfFreq)
	{
		Dtapi.DtOutpChannel* pDtOutpChannel = m_pDtOutpChannel;
		return (DTAPI_RESULT)((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, double, uint>)(int)(*(uint*)(*(int*)pDtOutpChannel + 296)))((nint)pDtOutpChannel, RfFreq);
	}

	public unsafe DTAPI_RESULT SetRfControl(int RfFreq)
	{
		Dtapi.DtOutpChannel* pDtOutpChannel = m_pDtOutpChannel;
		return (DTAPI_RESULT)((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, int, uint>)(int)(*(uint*)(*(int*)pDtOutpChannel + 292)))((nint)pDtOutpChannel, RfFreq);
	}

	public unsafe DTAPI_RESULT SetRfControl(long RfFreq)
	{
		Dtapi.DtOutpChannel* pDtOutpChannel = m_pDtOutpChannel;
		return (DTAPI_RESULT)((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, long, uint>)(int)(*(uint*)(*(int*)pDtOutpChannel + 300)))((nint)pDtOutpChannel, RfFreq);
	}

	public unsafe DTAPI_RESULT SetRfMode(int Sel, int Mode)
	{
		Dtapi.DtOutpChannel* pDtOutpChannel = m_pDtOutpChannel;
		return (DTAPI_RESULT)((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, int, int, uint>)(int)(*(uint*)(*(int*)pDtOutpChannel + 304)))((nint)pDtOutpChannel, Sel, Mode);
	}

	public unsafe DTAPI_RESULT SetRfMode(int RfMode)
	{
		Dtapi.DtOutpChannel* pDtOutpChannel = m_pDtOutpChannel;
		return (DTAPI_RESULT)((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, int, uint>)(int)(*(uint*)(*(int*)pDtOutpChannel + 308)))((nint)pDtOutpChannel, RfMode);
	}

	public unsafe DTAPI_RESULT SetSnr(int Mode, int Snr)
	{
		Dtapi.DtOutpChannel* pDtOutpChannel = m_pDtOutpChannel;
		return (DTAPI_RESULT)((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, int, int, uint>)(int)(*(uint*)(*(int*)pDtOutpChannel + 320)))((nint)pDtOutpChannel, Mode, Snr);
	}

	public unsafe DTAPI_RESULT SetSpiClk(int SpiClk)
	{
		Dtapi.DtOutpChannel* pDtOutpChannel = m_pDtOutpChannel;
		return (DTAPI_RESULT)((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, int, uint>)(int)(*(uint*)(*(int*)pDtOutpChannel + 324)))((nint)pDtOutpChannel, SpiClk);
	}

	public unsafe DTAPI_RESULT SetTsRateBps(DtFractionInt TsRate)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtFractionInt dtFractionInt);
		*(int*)(&dtFractionInt) = TsRate.m_Num;
		System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtFractionInt, int>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtFractionInt, 4)) = TsRate.m_Den;
		Dtapi.DtOutpChannel* pDtOutpChannel = m_pDtOutpChannel;
		return (DTAPI_RESULT)((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, Dtapi.DtFractionInt, uint>)(int)(*(uint*)(*(int*)pDtOutpChannel + 328)))((nint)pDtOutpChannel, dtFractionInt);
	}

	public unsafe DTAPI_RESULT SetTsRateBps(int TsRate)
	{
		Dtapi.DtOutpChannel* pDtOutpChannel = m_pDtOutpChannel;
		return (DTAPI_RESULT)((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, int, uint>)(int)(*(uint*)(*(int*)pDtOutpChannel + 332)))((nint)pDtOutpChannel, TsRate);
	}

	public unsafe DTAPI_RESULT SetTsRateRatio(int TsRate, int ClockRef)
	{
		Dtapi.DtOutpChannel* pDtOutpChannel = m_pDtOutpChannel;
		return (DTAPI_RESULT)((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, int, int, uint>)(int)(*(uint*)(*(int*)pDtOutpChannel + 336)))((nint)pDtOutpChannel, TsRate, ClockRef);
	}

	public unsafe DTAPI_RESULT SetTxControl(int TxControl)
	{
		Dtapi.DtOutpChannel* pDtOutpChannel = m_pDtOutpChannel;
		return (DTAPI_RESULT)((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, int, uint>)(int)(*(uint*)(*(int*)pDtOutpChannel + 340)))((nint)pDtOutpChannel, TxControl);
	}

	public unsafe DTAPI_RESULT SetTxMode(int TxPacketMode, int TxStuffMode)
	{
		Dtapi.DtOutpChannel* pDtOutpChannel = m_pDtOutpChannel;
		return (DTAPI_RESULT)((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, int, int, uint>)(int)(*(uint*)(*(int*)pDtOutpChannel + 344)))((nint)pDtOutpChannel, TxPacketMode, TxStuffMode);
	}

	public unsafe DTAPI_RESULT SetTxPolarity(int TxPolarity)
	{
		Dtapi.DtOutpChannel* pDtOutpChannel = m_pDtOutpChannel;
		return (DTAPI_RESULT)((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, int, uint>)(int)(*(uint*)(*(int*)pDtOutpChannel + 348)))((nint)pDtOutpChannel, TxPolarity);
	}

	public unsafe DTAPI_RESULT Write(byte[] rBuffer, int NumBytesToWrite, int SubChan)
	{
		fixed (byte* ptr = &rBuffer[0])
		{
			Dtapi.DtOutpChannel* pDtOutpChannel = m_pDtOutpChannel;
			return (DTAPI_RESULT)((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, sbyte*, int, int, uint>)(int)(*(uint*)(*(int*)pDtOutpChannel + 352)))((nint)pDtOutpChannel, (sbyte*)ptr, NumBytesToWrite, SubChan);
		}
	}

	public unsafe DTAPI_RESULT Write(byte[] rBuffer, int NumBytesToWrite)
	{
		fixed (byte* ptr = &rBuffer[0])
		{
			Dtapi.DtOutpChannel* pDtOutpChannel = m_pDtOutpChannel;
			return (DTAPI_RESULT)((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, sbyte*, int, int, uint>)(int)(*(uint*)(*(int*)pDtOutpChannel + 352)))((nint)pDtOutpChannel, (sbyte*)ptr, NumBytesToWrite, 0);
		}
	}

	protected internal unsafe DtOutpChannel(void* pDtOutpChannelDerived)
	{
		m_pDtOutpChannel = (Dtapi.DtOutpChannel*)pDtOutpChannelDerived;
		m_pDtOutpChannelDerived = pDtOutpChannelDerived;
	}

	public unsafe DtOutpChannel()
	{
		Dtapi.DtOutpChannel* ptr = (Dtapi.DtOutpChannel*)global::_003CModule_003E.@new(328u);
		Dtapi.DtOutpChannel* pDtOutpChannel;
		try
		{
			pDtOutpChannel = ((ptr == null) ? null : global::_003CModule_003E.Dtapi_002EDtOutpChannel_002E_007Bctor_007D(ptr));
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.delete(ptr, 328u);
			throw;
		}
		m_pDtOutpChannel = pDtOutpChannel;
		m_pDtOutpChannelDerived = null;
	}

	internal unsafe DtOutpChannel([MarshalAs(UnmanagedType.U1)] bool Init)
	{
		if (Init)
		{
			Dtapi.DtOutpChannel* ptr = (Dtapi.DtOutpChannel*)global::_003CModule_003E.@new(328u);
			Dtapi.DtOutpChannel* pDtOutpChannel;
			try
			{
				pDtOutpChannel = ((ptr == null) ? null : global::_003CModule_003E.Dtapi_002EDtOutpChannel_002E_007Bctor_007D(ptr));
			}
			catch
			{
				//try-fault
				global::_003CModule_003E.delete(ptr, 328u);
				throw;
			}
			m_pDtOutpChannel = pDtOutpChannel;
		}
		else
		{
			m_pDtOutpChannel = null;
		}
		m_pDtOutpChannelDerived = null;
	}

	private void _007EDtOutpChannel()
	{
		_0021DtOutpChannel();
	}

	private unsafe void _0021DtOutpChannel()
	{
		Dtapi.DtOutpChannel* pDtOutpChannel = m_pDtOutpChannel;
		if (pDtOutpChannel != null && m_pDtOutpChannelDerived == null)
		{
			Dtapi.DtOutpChannel* ptr = pDtOutpChannel;
			((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, uint, void*>)(int)(*(uint*)(int)(*(uint*)ptr)))((nint)ptr, 1u);
			m_pDtOutpChannel = null;
		}
	}

	[HandleProcessCorruptedStateExceptions]
	protected virtual void Dispose([MarshalAs(UnmanagedType.U1)] bool A_0)
	{
		if (A_0)
		{
			_0021DtOutpChannel();
			return;
		}
		try
		{
			_0021DtOutpChannel();
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

	~DtOutpChannel()
	{
		Dispose(A_0: false);
	}
}
