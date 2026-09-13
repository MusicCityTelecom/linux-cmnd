using System;
using System.Collections.Generic;
using System.Runtime.CompilerServices;
using System.Runtime.ExceptionServices;
using System.Runtime.InteropServices;
using Dtapi;
using std;

namespace DTAPINET;

public class DtAdvDemod : IDisposable
{
	public delegate void DtOutputRateChangedFunc(ref DtStreamSelPars rStreamSel, int Bitrate);

	public delegate void DtReadIqFunc(byte[] rIaBuffer, int IqBufSize, ref int IqLength);

	public delegate void DtWriteMeasFunc(ref DtStreamSelPars rStreamSel, DtMeasurement rData);

	public delegate void DtWriteStreamFunc(ref DtStreamSelPars rStreamSel, byte[] rBuffer, int Length);

	public delegate void DtWriteStreamWithTimeFunc(ref DtStreamSelPars rStreamSel, byte[] rBuffer, int Length, long Timestamp);

	internal unsafe Dtapi.DtAdvDemod* m_pDtAdvDemod;

	internal unsafe DtAdvDemodCallbackHelper* m_pAdvDemodCallbackHelper;

	internal DtOutputRateChangedFunc m_DtOutputRateChangedFunc;

	internal DtReadIqFunc m_DtReadIqFunc;

	internal DtWriteMeasFunc m_DtWriteMeasFunc;

	internal DtWriteStreamFunc m_DtWriteStreamFunc;

	internal DtWriteStreamWithTimeFunc m_DtWriteStreamWithTimeFunc;

	protected internal unsafe void* m_pDtAdvDemodDerived;

	public unsafe DtHwFuncDesc HwFuncDesc()
	{
		DtHwFuncDesc dtHwFuncDesc = new DtHwFuncDesc();
		dtHwFuncDesc.ConvertFromUnmgd((Dtapi.DtHwFuncDesc*)((byte*)m_pDtAdvDemod + 8));
		return dtHwFuncDesc;
	}

	public unsafe int Category()
	{
		return ((int*)m_pDtAdvDemod)[2];
	}

	public unsafe int FirmwareVersion()
	{
		return ((int*)m_pDtAdvDemod)[17];
	}

	[return: MarshalAs(UnmanagedType.U1)]
	public unsafe bool IsAttached()
	{
		return global::_003CModule_003E.Dtapi_002EDtAdvDemod_002EIsAttached(m_pDtAdvDemod);
	}

	public unsafe int TypeNumber()
	{
		return ((int*)m_pDtAdvDemod)[9];
	}

	public unsafe DTAPI_RESULT AttachToPort(DtDevice rDtDvc, int Port, [MarshalAs(UnmanagedType.U1)] bool Exclusive, [MarshalAs(UnmanagedType.U1)] bool ProbeOnly)
	{
		return (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtAdvDemod_002EAttachToPort(m_pDtAdvDemod, rDtDvc.pDtDvc, Port, Exclusive, ProbeOnly);
	}

	public unsafe DTAPI_RESULT AttachToPort(DtDevice rDtDvc, int Port, [MarshalAs(UnmanagedType.U1)] bool Exclusive)
	{
		return (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtAdvDemod_002EAttachToPort(m_pDtAdvDemod, rDtDvc.pDtDvc, Port, Exclusive, false);
	}

	public unsafe DTAPI_RESULT AttachToPort(DtDevice rDtDvc, int Port)
	{
		return (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtAdvDemod_002EAttachToPort(m_pDtAdvDemod, rDtDvc.pDtDvc, Port, true, false);
	}

	public unsafe DTAPI_RESULT AttachVirtual(DtDevice rDtDvc, DtReadIqFunc rCallback)
	{
		m_DtReadIqFunc = rCallback;
		return (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtAdvDemod_002EAttachVirtual(m_pDtAdvDemod, rDtDvc.pDtDvc, (delegate* unmanaged[Cdecl, Cdecl]<void*, byte*, int, int*, void>)global::_003CModule_003E.__unep_0040_003FDtReadIqFunc_0040DtAdvDemodCallbackHelper_0040DTAPINET_0040_0040_0024_0024FSAXPAXPAEHAAH_0040Z, m_pAdvDemodCallbackHelper);
	}

	public unsafe DTAPI_RESULT ClearFlags(int Latched)
	{
		return (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtAdvDemod_002EClearFlags(m_pDtAdvDemod, Latched);
	}

	public unsafe DTAPI_RESULT CloseStream(int Id)
	{
		return (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtAdvDemod_002ECloseStream(m_pDtAdvDemod, Id);
	}

	public unsafe DTAPI_RESULT Detach(int DetachMode)
	{
		m_DtOutputRateChangedFunc = null;
		m_DtReadIqFunc = null;
		m_DtWriteMeasFunc = null;
		m_DtWriteStreamFunc = null;
		m_DtWriteStreamWithTimeFunc = null;
		return (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtAdvDemod_002EDetach(m_pDtAdvDemod, DetachMode);
	}

	public unsafe DTAPI_RESULT GetDemodControl(DtDemodPars DemodPars)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDemodPars dtDemodPars);
		global::_003CModule_003E.Dtapi_002EDtDemodPars_002E_007Bctor_007D(&dtDemodPars);
		DTAPI_RESULT result;
		try
		{
			result = (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtAdvDemod_002EGetDemodControl(m_pDtAdvDemod, &dtDemodPars);
			DemodPars.ConvertFromUnmgd(&dtDemodPars);
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtDemodPars*, void>)(&global::_003CModule_003E.Dtapi_002EDtDemodPars_002E_007Bdtor_007D), &dtDemodPars);
			throw;
		}
		global::_003CModule_003E.Dtapi_002EDtDemodPars_002E_007Bdtor_007D(&dtDemodPars);
		return result;
	}

	public unsafe DTAPI_RESULT GetDescriptor(ref DtHwFuncDesc rHwFuncDesc)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtHwFuncDesc dtHwFuncDesc);
		global::_003CModule_003E.Dtapi_002EDtHwFuncDesc_002E_007Bctor_007D(&dtHwFuncDesc);
		uint result = global::_003CModule_003E.Dtapi_002EDtAdvDemod_002EGetDescriptor(m_pDtAdvDemod, &dtHwFuncDesc);
		(rHwFuncDesc = new DtHwFuncDesc()).ConvertFromUnmgd(&dtHwFuncDesc);
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetFlags(ref int Flags, ref int Latched)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num);
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num2);
		uint result = global::_003CModule_003E.Dtapi_002EDtAdvDemod_002EGetFlags(m_pDtAdvDemod, &num, &num2);
		Flags = num;
		Latched = num2;
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetIoConfig(int Group, ref int Value, ref int SubValue, ref long ParXtra0, ref long ParXtra1)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num);
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num2);
		System.Runtime.CompilerServices.Unsafe.SkipInit(out long num3);
		System.Runtime.CompilerServices.Unsafe.SkipInit(out long num4);
		uint result = global::_003CModule_003E.Dtapi_002EDtAdvDemod_002EGetIoConfig(m_pDtAdvDemod, Group, &num, &num2, &num3, &num4);
		Value = num;
		SubValue = num2;
		ParXtra0 = num3;
		ParXtra1 = num4;
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetIoConfig(int Group, ref int Value, ref int SubValue, ref long ParXtra0)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num);
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num2);
		System.Runtime.CompilerServices.Unsafe.SkipInit(out long num3);
		System.Runtime.CompilerServices.Unsafe.SkipInit(out long num4);
		uint result = global::_003CModule_003E.Dtapi_002EDtAdvDemod_002EGetIoConfig(m_pDtAdvDemod, Group, &num, &num2, &num3, &num4);
		Value = num;
		SubValue = num2;
		ParXtra0 = num3;
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetIoConfig(int Group, ref int Value, ref int SubValue)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num);
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num2);
		System.Runtime.CompilerServices.Unsafe.SkipInit(out long num3);
		System.Runtime.CompilerServices.Unsafe.SkipInit(out long num4);
		uint result = global::_003CModule_003E.Dtapi_002EDtAdvDemod_002EGetIoConfig(m_pDtAdvDemod, Group, &num, &num2, &num3, &num4);
		Value = num;
		SubValue = num2;
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetIoConfig(int Group, ref int Value)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num);
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num2);
		System.Runtime.CompilerServices.Unsafe.SkipInit(out long num3);
		System.Runtime.CompilerServices.Unsafe.SkipInit(out long num4);
		uint result = global::_003CModule_003E.Dtapi_002EDtAdvDemod_002EGetIoConfig(m_pDtAdvDemod, Group, &num, &num2, &num3, &num4);
		Value = num;
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetPars(int Count, DtPar[] rPars)
	{
		uint num2;
		if ((uint)Count <= 89478485u)
		{
			uint num = (uint)(Count * 48);
			if (num <= 4294967291u)
			{
				num2 = num + 4;
				goto IL_0022;
			}
		}
		num2 = uint.MaxValue;
		goto IL_0022;
		IL_0022:
		Dtapi.DtPar* ptr = (Dtapi.DtPar*)global::_003CModule_003E.new_005B_005D(num2);
		Dtapi.DtPar* ptr3;
		try
		{
			if (ptr != null)
			{
				*(int*)ptr = Count;
				Dtapi.DtPar* ptr2 = (Dtapi.DtPar*)((byte*)ptr + 4);
				global::_003CModule_003E.__ehvec_ctor(ptr2, 48u, (uint)Count, (delegate*<void*, void>)(delegate*<Dtapi.DtPar*, Dtapi.DtPar*>)(&global::_003CModule_003E.Dtapi_002EDtPar_002E_007Bctor_007D), (delegate*<void*, void>)(delegate*<Dtapi.DtPar*, void>)(&global::_003CModule_003E.Dtapi_002EDtPar_002E_007Bdtor_007D));
				ptr3 = ptr2;
			}
			else
			{
				ptr3 = null;
			}
		}
		catch
		{
			//try-fault
			uint num3 = (((uint)Count > 89478485u) ? uint.MaxValue : ((uint)(Count * 48)));
			uint num4 = ((num3 > 4294967291u) ? uint.MaxValue : (num3 + 4));
			global::_003CModule_003E.delete_005B_005D(ptr, num4);
			throw;
		}
		int num5 = 0;
		if (0 < Count)
		{
			Dtapi.DtPar* ptr4 = ptr3;
			do
			{
				global::_003CModule_003E.Dtapi_002EDtPar_002E_003D(ptr4, rPars[num5].m_pDtPar);
				num5++;
				ptr4 = (Dtapi.DtPar*)((byte*)ptr4 + 48);
			}
			while (num5 < Count);
		}
		uint num6 = global::_003CModule_003E.Dtapi_002EDtAdvDemod_002EGetPars(m_pDtAdvDemod, Count, ptr3);
		if (num6 == 0)
		{
			int num7 = 0;
			if (0 < Count)
			{
				Dtapi.DtPar* ptr5 = ptr3;
				do
				{
					global::_003CModule_003E.Dtapi_002EDtPar_002E_003D(rPars[num7].m_pDtPar, ptr5);
					num7++;
					ptr5 = (Dtapi.DtPar*)((byte*)ptr5 + 48);
				}
				while (num7 < Count);
			}
		}
		if (ptr3 != null)
		{
			Dtapi.DtPar* ptr6 = (Dtapi.DtPar*)((byte*)ptr3 - 4);
			if (*(int*)ptr6 != 0)
			{
				((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, uint, void*>)(int)(*(uint*)(int)(*(uint*)ptr3)))((nint)ptr3, 3u);
			}
			else
			{
				global::_003CModule_003E.delete_005B_005D(ptr6);
			}
		}
		return (DTAPI_RESULT)num6;
	}

	public unsafe DTAPI_RESULT GetRxControl(ref int RxControl)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num);
		uint result = global::_003CModule_003E.Dtapi_002EDtAdvDemod_002EGetRxControl(m_pDtAdvDemod, &num);
		RxControl = num;
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetStatistics(int Type, ref bool Statistic)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out bool flag);
		uint result = global::_003CModule_003E.Dtapi_002EDtAdvDemod_002EGetStatistic(m_pDtAdvDemod, Type, &flag);
		Statistic = flag;
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetStatistics(int Type, ref double Statistic)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out double num);
		uint result = global::_003CModule_003E.Dtapi_002EDtAdvDemod_002EGetStatistic(m_pDtAdvDemod, Type, &num);
		Statistic = num;
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetStatistics(int Type, ref int Statistic)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num);
		uint result = global::_003CModule_003E.Dtapi_002EDtAdvDemod_002EGetStatistic(m_pDtAdvDemod, Type, &num);
		Statistic = num;
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetStatistics(int Count, DtStatistic[] rStatistics)
	{
		uint num2;
		if ((uint)Count <= 89478485u)
		{
			uint num = (uint)(Count * 48);
			if (num <= 4294967291u)
			{
				num2 = num + 4;
				goto IL_0022;
			}
		}
		num2 = uint.MaxValue;
		goto IL_0022;
		IL_0022:
		Dtapi.DtStatistic* ptr = (Dtapi.DtStatistic*)global::_003CModule_003E.new_005B_005D(num2);
		Dtapi.DtStatistic* ptr3;
		try
		{
			if (ptr != null)
			{
				*(int*)ptr = Count;
				Dtapi.DtStatistic* ptr2 = (Dtapi.DtStatistic*)((byte*)ptr + 4);
				global::_003CModule_003E.__ehvec_ctor(ptr2, 48u, (uint)Count, (delegate*<void*, void>)(delegate*<Dtapi.DtStatistic*, Dtapi.DtStatistic*>)(&global::_003CModule_003E.Dtapi_002EDtStatistic_002E_007Bctor_007D), (delegate*<void*, void>)(delegate*<Dtapi.DtStatistic*, void>)(&global::_003CModule_003E.Dtapi_002EDtStatistic_002E_007Bdtor_007D));
				ptr3 = ptr2;
			}
			else
			{
				ptr3 = null;
			}
		}
		catch
		{
			//try-fault
			uint num3 = (((uint)Count > 89478485u) ? uint.MaxValue : ((uint)(Count * 48)));
			uint num4 = ((num3 > 4294967291u) ? uint.MaxValue : (num3 + 4));
			global::_003CModule_003E.delete_005B_005D(ptr, num4);
			throw;
		}
		int num5 = 0;
		if (0 < Count)
		{
			Dtapi.DtStatistic* ptr4 = ptr3;
			do
			{
				global::_003CModule_003E.Dtapi_002EDtStatistic_002E_003D(ptr4, rStatistics[num5].m_pDtStatistic);
				num5++;
				ptr4 = (Dtapi.DtStatistic*)((byte*)ptr4 + 48);
			}
			while (num5 < Count);
		}
		uint num6 = global::_003CModule_003E.Dtapi_002EDtAdvDemod_002EGetStatistics(m_pDtAdvDemod, Count, ptr3);
		if (num6 == 0)
		{
			int num7 = 0;
			if (0 < Count)
			{
				Dtapi.DtStatistic* ptr5 = ptr3;
				do
				{
					global::_003CModule_003E.Dtapi_002EDtStatistic_002E_003D(rStatistics[num7].m_pDtStatistic, ptr5);
					num7++;
					ptr5 = (Dtapi.DtStatistic*)((byte*)ptr5 + 48);
				}
				while (num7 < Count);
			}
		}
		if (ptr3 != null)
		{
			Dtapi.DtStatistic* ptr6 = (Dtapi.DtStatistic*)((byte*)ptr3 - 4);
			if (*(int*)ptr6 != 0)
			{
				((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, uint, void*>)(int)(*(uint*)(int)(*(uint*)ptr3)))((nint)ptr3, 3u);
			}
			else
			{
				global::_003CModule_003E.delete_005B_005D(ptr6);
			}
		}
		return (DTAPI_RESULT)num6;
	}

	public unsafe DTAPI_RESULT GetStreamSelection(ref List<DtStreamSelPars> rStreamSels)
	{
		rStreamSels = new List<DtStreamSelPars>();
		System.Runtime.CompilerServices.Unsafe.SkipInit(out vector_003CDtapi_003A_003ADtStreamSelPars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtStreamSelPars_003E_0020_003E obj);
		*(int*)(&obj) = 0;
		System.Runtime.CompilerServices.Unsafe.As<vector_003CDtapi_003A_003ADtStreamSelPars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtStreamSelPars_003E_0020_003E, int>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref obj, 4)) = 0;
		System.Runtime.CompilerServices.Unsafe.As<vector_003CDtapi_003A_003ADtStreamSelPars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtStreamSelPars_003E_0020_003E, int>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref obj, 8)) = 0;
		uint result;
		try
		{
			result = global::_003CModule_003E.Dtapi_002EDtAdvDemod_002EGetStreamSelection(m_pDtAdvDemod, &obj);
			uint num = 0u;
			if (0u < (uint)(System.Runtime.CompilerServices.Unsafe.As<vector_003CDtapi_003A_003ADtStreamSelPars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtStreamSelPars_003E_0020_003E, int>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref obj, 4)) - *(int*)(&obj) >> 5))
			{
				int num2 = 0;
				do
				{
					DtStreamSelPars item = default(DtStreamSelPars);
					item.ConvertFromUnmgd((Dtapi.DtStreamSelPars*)(num2 + *(int*)(&obj)));
					rStreamSels.Add(item);
					num++;
					num2 += 32;
				}
				while (num < (uint)(System.Runtime.CompilerServices.Unsafe.As<vector_003CDtapi_003A_003ADtStreamSelPars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtStreamSelPars_003E_0020_003E, int>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref obj, 4)) - *(int*)(&obj) >> 5));
			}
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<vector_003CDtapi_003A_003ADtStreamSelPars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtStreamSelPars_003E_0020_003E*, void>)(&global::_003CModule_003E.std_002Evector_003CDtapi_003A_003ADtStreamSelPars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtStreamSelPars_003E_0020_003E_002E_007Bdtor_007D), &obj);
			throw;
		}
		global::_003CModule_003E.std_002Evector_003CDtapi_003A_003ADtStreamSelPars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtStreamSelPars_003E_0020_003E_002E_Tidy(&obj);
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetSupportedStatistics(ref int Count, DtStatistic[] rStatistics)
	{
		int num = Count;
		int num2 = num;
		uint num3 = (uint)num;
		uint num5;
		if (num3 <= 89478485)
		{
			uint num4 = num3 * 48;
			if (num4 <= 4294967291u)
			{
				num5 = num4 + 4;
				goto IL_002a;
			}
		}
		num5 = uint.MaxValue;
		goto IL_002a;
		IL_002a:
		Dtapi.DtStatistic* ptr = (Dtapi.DtStatistic*)global::_003CModule_003E.new_005B_005D(num5);
		Dtapi.DtStatistic* ptr3;
		try
		{
			if (ptr != null)
			{
				*(uint*)ptr = num3;
				Dtapi.DtStatistic* ptr2 = (Dtapi.DtStatistic*)((byte*)ptr + 4);
				global::_003CModule_003E.__ehvec_ctor(ptr2, 48u, num3, (delegate*<void*, void>)(delegate*<Dtapi.DtStatistic*, Dtapi.DtStatistic*>)(&global::_003CModule_003E.Dtapi_002EDtStatistic_002E_007Bctor_007D), (delegate*<void*, void>)(delegate*<Dtapi.DtStatistic*, void>)(&global::_003CModule_003E.Dtapi_002EDtStatistic_002E_007Bdtor_007D));
				ptr3 = ptr2;
			}
			else
			{
				ptr3 = null;
			}
		}
		catch
		{
			//try-fault
			uint num6 = num3;
			uint num7 = ((num6 > 89478485) ? uint.MaxValue : (num6 * 48));
			uint num8 = ((num7 > 4294967291u) ? uint.MaxValue : (num7 + 4));
			global::_003CModule_003E.delete_005B_005D(ptr, num8);
			throw;
		}
		uint num9 = global::_003CModule_003E.Dtapi_002EDtAdvDemod_002EGetSupportedStatistics(m_pDtAdvDemod, &num2, ptr3);
		if (num9 == 0)
		{
			int num10 = 0;
			if (0 < num2)
			{
				Dtapi.DtStatistic* ptr4 = ptr3;
				do
				{
					global::_003CModule_003E.Dtapi_002EDtStatistic_002E_003D(rStatistics[num10].m_pDtStatistic, ptr4);
					num10++;
					ptr4 = (Dtapi.DtStatistic*)((byte*)ptr4 + 48);
				}
				while (num10 < num2);
			}
		}
		if (ptr3 != null)
		{
			Dtapi.DtStatistic* ptr5 = (Dtapi.DtStatistic*)((byte*)ptr3 - 4);
			if (*(int*)ptr5 != 0)
			{
				((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, uint, void*>)(int)(*(uint*)(int)(*(uint*)ptr3)))((nint)ptr3, 3u);
			}
			else
			{
				global::_003CModule_003E.delete_005B_005D(ptr5);
			}
		}
		Count = num2;
		return (DTAPI_RESULT)num9;
	}

	public unsafe DTAPI_RESULT GetTsRateBps(int Id, ref int TsRate)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num);
		uint result = global::_003CModule_003E.Dtapi_002EDtAdvDemod_002EGetTsRateBps(m_pDtAdvDemod, Id, &num);
		TsRate = num;
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetTunerFrequency(ref long FreqHz)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out long num);
		uint result = global::_003CModule_003E.Dtapi_002EDtAdvDemod_002EGetTunerFrequency(m_pDtAdvDemod, &num);
		FreqHz = num;
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT LedControl(int LedControl)
	{
		return (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtAdvDemod_002ELedControl(m_pDtAdvDemod, LedControl);
	}

	public unsafe DTAPI_RESULT OpenStream(ValueType rStreamSel)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtStreamSelPars dtStreamSelPars);
		((DtStreamSelPars)rStreamSel).ConvertToUnmgd(&dtStreamSelPars);
		return (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtAdvDemod_002EOpenStream(m_pDtAdvDemod, dtStreamSelPars);
	}

	public unsafe DTAPI_RESULT RegisterCallback(DtWriteMeasFunc rCallback)
	{
		m_DtWriteMeasFunc = rCallback;
		return (DTAPI_RESULT)((!(rCallback != null)) ? global::_003CModule_003E.Dtapi_002EDtAdvDemod_002ERegisterCallback(m_pDtAdvDemod, (delegate* unmanaged[Cdecl, Cdecl]<void*, Dtapi.DtStreamSelPars*, Dtapi.DtMeasurement*, void>)null, (void*)null) : global::_003CModule_003E.Dtapi_002EDtAdvDemod_002ERegisterCallback(m_pDtAdvDemod, (delegate* unmanaged[Cdecl, Cdecl]<void*, Dtapi.DtStreamSelPars*, Dtapi.DtMeasurement*, void>)global::_003CModule_003E.__unep_0040_003FDtWriteMeasFunc_0040DtAdvDemodCallbackHelper_0040DTAPINET_0040_0040_0024_0024FSAXPAXAAUDtStreamSelPars_0040Dtapi_0040_0040PAUDtMeasurement_00404_0040_0040Z, m_pAdvDemodCallbackHelper));
	}

	public unsafe DTAPI_RESULT RegisterCallback(DtOutputRateChangedFunc rCallback)
	{
		m_DtOutputRateChangedFunc = rCallback;
		return (DTAPI_RESULT)((!(rCallback != null)) ? global::_003CModule_003E.Dtapi_002EDtAdvDemod_002ERegisterCallback(m_pDtAdvDemod, (delegate* unmanaged[Cdecl, Cdecl]<void*, Dtapi.DtStreamSelPars*, int, void>)null, (void*)null) : global::_003CModule_003E.Dtapi_002EDtAdvDemod_002ERegisterCallback(m_pDtAdvDemod, (delegate* unmanaged[Cdecl, Cdecl]<void*, Dtapi.DtStreamSelPars*, int, void>)global::_003CModule_003E.__unep_0040_003FDtOutputRateChangedFunc_0040DtAdvDemodCallbackHelper_0040DTAPINET_0040_0040_0024_0024FSAXPAXAAUDtStreamSelPars_0040Dtapi_0040_0040H_0040Z, m_pAdvDemodCallbackHelper));
	}

	public unsafe DTAPI_RESULT RegisterCallback(DtWriteStreamWithTimeFunc rCallback)
	{
		m_DtWriteStreamWithTimeFunc = rCallback;
		return (DTAPI_RESULT)((!(rCallback != null)) ? global::_003CModule_003E.Dtapi_002EDtAdvDemod_002ERegisterCallback(m_pDtAdvDemod, (delegate* unmanaged[Cdecl, Cdecl]<void*, Dtapi.DtStreamSelPars*, byte*, int, long, void>)null, (void*)null) : global::_003CModule_003E.Dtapi_002EDtAdvDemod_002ERegisterCallback(m_pDtAdvDemod, (delegate* unmanaged[Cdecl, Cdecl]<void*, Dtapi.DtStreamSelPars*, byte*, int, long, void>)global::_003CModule_003E.__unep_0040_003FDtWriteStreamWithTimeFunc_0040DtAdvDemodCallbackHelper_0040DTAPINET_0040_0040_0024_0024FSAXPAXAAUDtStreamSelPars_0040Dtapi_0040_0040PBEH_J_0040Z, m_pAdvDemodCallbackHelper));
	}

	public unsafe DTAPI_RESULT RegisterCallback(DtWriteStreamFunc rCallback)
	{
		m_DtWriteStreamFunc = rCallback;
		return (DTAPI_RESULT)((!(rCallback != null)) ? global::_003CModule_003E.Dtapi_002EDtAdvDemod_002ERegisterCallback(m_pDtAdvDemod, (delegate* unmanaged[Cdecl, Cdecl]<void*, Dtapi.DtStreamSelPars*, byte*, int, void>)null, (void*)null) : global::_003CModule_003E.Dtapi_002EDtAdvDemod_002ERegisterCallback(m_pDtAdvDemod, (delegate* unmanaged[Cdecl, Cdecl]<void*, Dtapi.DtStreamSelPars*, byte*, int, void>)global::_003CModule_003E.__unep_0040_003FDtWriteStreamFunc_0040DtAdvDemodCallbackHelper_0040DTAPINET_0040_0040_0024_0024FSAXPAXAAUDtStreamSelPars_0040Dtapi_0040_0040PBEH_0040Z, m_pAdvDemodCallbackHelper));
	}

	public unsafe DTAPI_RESULT Reset(int ResetMode)
	{
		return (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtAdvDemod_002EReset(m_pDtAdvDemod, ResetMode);
	}

	public unsafe DTAPI_RESULT SetAntPower(int AntPower)
	{
		return (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtAdvDemod_002ESetAntPower(m_pDtAdvDemod, AntPower);
	}

	public unsafe DTAPI_RESULT SetDemodControl(DtDemodPars DemodPars)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDemodPars dtDemodPars);
		global::_003CModule_003E.Dtapi_002EDtDemodPars_002E_007Bctor_007D(&dtDemodPars);
		DTAPI_RESULT result;
		try
		{
			DemodPars.ConvertToUnmgd(&dtDemodPars);
			result = (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtAdvDemod_002ESetDemodControl(m_pDtAdvDemod, &dtDemodPars);
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtDemodPars*, void>)(&global::_003CModule_003E.Dtapi_002EDtDemodPars_002E_007Bdtor_007D), &dtDemodPars);
			throw;
		}
		global::_003CModule_003E.Dtapi_002EDtDemodPars_002E_007Bdtor_007D(&dtDemodPars);
		return result;
	}

	public unsafe DTAPI_RESULT SetIoConfig(int Group, int Value, int SubValue, long ParXtra0)
	{
		return (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtAdvDemod_002ESetIoConfig(m_pDtAdvDemod, Group, Value, SubValue, ParXtra0, -1L);
	}

	public unsafe DTAPI_RESULT SetIoConfig(int Group, int Value, int SubValue)
	{
		return (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtAdvDemod_002ESetIoConfig(m_pDtAdvDemod, Group, Value, SubValue, -1L, -1L);
	}

	public unsafe DTAPI_RESULT SetIoConfig(int Group, int Value)
	{
		return (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtAdvDemod_002ESetIoConfig(m_pDtAdvDemod, Group, Value, -1, -1L, -1L);
	}

	public unsafe DTAPI_RESULT SetIoConfig(int Group, int Value, int SubValue, long ParXtra0, long ParXtra1)
	{
		return (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtAdvDemod_002ESetIoConfig(m_pDtAdvDemod, Group, Value, SubValue, ParXtra0, ParXtra1);
	}

	public unsafe DTAPI_RESULT SetPars(int Count, DtPar[] rPars)
	{
		uint num2;
		if ((uint)Count <= 89478485u)
		{
			uint num = (uint)(Count * 48);
			if (num <= 4294967291u)
			{
				num2 = num + 4;
				goto IL_0022;
			}
		}
		num2 = uint.MaxValue;
		goto IL_0022;
		IL_0022:
		Dtapi.DtPar* ptr = (Dtapi.DtPar*)global::_003CModule_003E.new_005B_005D(num2);
		Dtapi.DtPar* ptr3;
		try
		{
			if (ptr != null)
			{
				*(int*)ptr = Count;
				Dtapi.DtPar* ptr2 = (Dtapi.DtPar*)((byte*)ptr + 4);
				global::_003CModule_003E.__ehvec_ctor(ptr2, 48u, (uint)Count, (delegate*<void*, void>)(delegate*<Dtapi.DtPar*, Dtapi.DtPar*>)(&global::_003CModule_003E.Dtapi_002EDtPar_002E_007Bctor_007D), (delegate*<void*, void>)(delegate*<Dtapi.DtPar*, void>)(&global::_003CModule_003E.Dtapi_002EDtPar_002E_007Bdtor_007D));
				ptr3 = ptr2;
			}
			else
			{
				ptr3 = null;
			}
		}
		catch
		{
			//try-fault
			uint num3 = (((uint)Count > 89478485u) ? uint.MaxValue : ((uint)(Count * 48)));
			uint num4 = ((num3 > 4294967291u) ? uint.MaxValue : (num3 + 4));
			global::_003CModule_003E.delete_005B_005D(ptr, num4);
			throw;
		}
		int num5 = 0;
		if (0 < Count)
		{
			Dtapi.DtPar* ptr4 = ptr3;
			do
			{
				global::_003CModule_003E.Dtapi_002EDtPar_002E_003D(ptr4, rPars[num5].m_pDtPar);
				num5++;
				ptr4 = (Dtapi.DtPar*)((byte*)ptr4 + 48);
			}
			while (num5 < Count);
		}
		uint result = global::_003CModule_003E.Dtapi_002EDtAdvDemod_002ESetPars(m_pDtAdvDemod, Count, ptr3);
		if (ptr3 != null)
		{
			Dtapi.DtPar* ptr5 = (Dtapi.DtPar*)((byte*)ptr3 - 4);
			if (*(int*)ptr5 != 0)
			{
				((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, uint, void*>)(int)(*(uint*)(int)(*(uint*)ptr3)))((nint)ptr3, 3u);
			}
			else
			{
				global::_003CModule_003E.delete_005B_005D(ptr5);
			}
		}
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT SetRxControl(int RxControl)
	{
		return (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtAdvDemod_002ESetRxControl(m_pDtAdvDemod, RxControl);
	}

	public unsafe DTAPI_RESULT SetTunerFrequency(long FreqHz)
	{
		return (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtAdvDemod_002ESetTunerFrequency(m_pDtAdvDemod, FreqHz);
	}

	public unsafe DTAPI_RESULT Tune(long FreqHz, DtDemodPars DemodPars)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDemodPars dtDemodPars);
		global::_003CModule_003E.Dtapi_002EDtDemodPars_002E_007Bctor_007D(&dtDemodPars);
		DTAPI_RESULT result;
		try
		{
			DemodPars.ConvertToUnmgd(&dtDemodPars);
			result = (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtAdvDemod_002ETune(m_pDtAdvDemod, FreqHz, &dtDemodPars);
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtDemodPars*, void>)(&global::_003CModule_003E.Dtapi_002EDtDemodPars_002E_007Bdtor_007D), &dtDemodPars);
			throw;
		}
		global::_003CModule_003E.Dtapi_002EDtDemodPars_002E_007Bdtor_007D(&dtDemodPars);
		return result;
	}

	protected internal unsafe DtAdvDemod(void* pDtAdvDemodDerived)
	{
		m_pDtAdvDemod = (Dtapi.DtAdvDemod*)pDtAdvDemodDerived;
		m_pDtAdvDemodDerived = pDtAdvDemodDerived;
		DtAdvDemodCallbackHelper* ptr = (DtAdvDemodCallbackHelper*)global::_003CModule_003E.@new(4u);
		DtAdvDemodCallbackHelper* pAdvDemodCallbackHelper;
		try
		{
			if (ptr != null)
			{
				*(int*)ptr = (int)((IntPtr)GCHandle.Alloc(this)).ToPointer();
				pAdvDemodCallbackHelper = ptr;
			}
			else
			{
				pAdvDemodCallbackHelper = null;
			}
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.delete(ptr, 4u);
			throw;
		}
		m_pAdvDemodCallbackHelper = pAdvDemodCallbackHelper;
	}

	public unsafe DtAdvDemod()
	{
		Dtapi.DtAdvDemod* ptr = (Dtapi.DtAdvDemod*)global::_003CModule_003E.@new(352u);
		Dtapi.DtAdvDemod* pDtAdvDemod;
		try
		{
			pDtAdvDemod = ((ptr == null) ? null : global::_003CModule_003E.Dtapi_002EDtAdvDemod_002E_007Bctor_007D(ptr));
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.delete(ptr, 352u);
			throw;
		}
		m_pDtAdvDemod = pDtAdvDemod;
		m_pDtAdvDemodDerived = null;
		DtAdvDemodCallbackHelper* ptr2 = (DtAdvDemodCallbackHelper*)global::_003CModule_003E.@new(4u);
		DtAdvDemodCallbackHelper* pAdvDemodCallbackHelper;
		try
		{
			if (ptr2 != null)
			{
				*(int*)ptr2 = (int)((IntPtr)GCHandle.Alloc(this)).ToPointer();
				pAdvDemodCallbackHelper = ptr2;
			}
			else
			{
				pAdvDemodCallbackHelper = null;
			}
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.delete(ptr2, 4u);
			throw;
		}
		m_pAdvDemodCallbackHelper = pAdvDemodCallbackHelper;
	}

	internal unsafe DtAdvDemod([MarshalAs(UnmanagedType.U1)] bool Init)
	{
		if (Init)
		{
			Dtapi.DtAdvDemod* ptr = (Dtapi.DtAdvDemod*)global::_003CModule_003E.@new(352u);
			Dtapi.DtAdvDemod* pDtAdvDemod;
			try
			{
				pDtAdvDemod = ((ptr == null) ? null : global::_003CModule_003E.Dtapi_002EDtAdvDemod_002E_007Bctor_007D(ptr));
			}
			catch
			{
				//try-fault
				global::_003CModule_003E.delete(ptr, 352u);
				throw;
			}
			m_pDtAdvDemod = pDtAdvDemod;
		}
		else
		{
			m_pDtAdvDemod = null;
		}
		m_pDtAdvDemodDerived = null;
		DtAdvDemodCallbackHelper* ptr2 = (DtAdvDemodCallbackHelper*)global::_003CModule_003E.@new(4u);
		DtAdvDemodCallbackHelper* pAdvDemodCallbackHelper;
		try
		{
			if (ptr2 != null)
			{
				*(int*)ptr2 = (int)((IntPtr)GCHandle.Alloc(this)).ToPointer();
				pAdvDemodCallbackHelper = ptr2;
			}
			else
			{
				pAdvDemodCallbackHelper = null;
			}
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.delete(ptr2, 4u);
			throw;
		}
		m_pAdvDemodCallbackHelper = pAdvDemodCallbackHelper;
	}

	private void _007EDtAdvDemod()
	{
		_0021DtAdvDemod();
	}

	private unsafe void _0021DtAdvDemod()
	{
		Dtapi.DtAdvDemod* pDtAdvDemod = m_pDtAdvDemod;
		if (pDtAdvDemod != null && m_pDtAdvDemodDerived == null)
		{
			Dtapi.DtAdvDemod* ptr = pDtAdvDemod;
			((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, uint, void*>)(int)(*(uint*)(int)(*(uint*)ptr)))((nint)ptr, 1u);
			m_pDtAdvDemod = null;
		}
		DtAdvDemodCallbackHelper* pAdvDemodCallbackHelper = m_pAdvDemodCallbackHelper;
		if (pAdvDemodCallbackHelper != null)
		{
			DtAdvDemodCallbackHelper* ptr2 = pAdvDemodCallbackHelper;
			global::_003CModule_003E.gcroot_003CDTAPINET_003A_003ADtAdvDemod_0020_005E_003E_002E_007Bdtor_007D((gcroot_003CDTAPINET_003A_003ADtAdvDemod_0020_005E_003E*)ptr2);
			global::_003CModule_003E.delete(ptr2, 4u);
			m_pAdvDemodCallbackHelper = null;
		}
	}

	[HandleProcessCorruptedStateExceptions]
	protected virtual void Dispose([MarshalAs(UnmanagedType.U1)] bool A_0)
	{
		if (A_0)
		{
			_0021DtAdvDemod();
			return;
		}
		try
		{
			_0021DtAdvDemod();
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

	~DtAdvDemod()
	{
		Dispose(A_0: false);
	}
}
