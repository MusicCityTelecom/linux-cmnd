using System;
using System.Runtime.CompilerServices;
using System.Runtime.ExceptionServices;
using System.Runtime.InteropServices;
using Dtapi;
using std;

namespace DTAPINET;

public class DtMplpOutpChannel : DtOutpChannel
{
	[return: MarshalAs(UnmanagedType.U1)]
	public delegate bool VirtOutDataHandler(DtVirtualOutData OutData);

	public VirtOutDataHandler m_VirtDataGenerated;

	internal unsafe Dtapi.DtMplpOutpChannel* m_pDtMplpOutpChannel;

	internal unsafe VirtOutDataHandlerHelper* m_pVirtOutDataHandlerHelper;

	public unsafe DTAPI_RESULT AttachVirtual(DtDevice rDtDvc, VirtOutDataHandler DataHandler)
	{
		m_VirtDataGenerated = DataHandler;
		Dtapi.DtMplpOutpChannel* pDtMplpOutpChannel = m_pDtMplpOutpChannel;
		return (DTAPI_RESULT)((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, Dtapi.DtDevice*, delegate* unmanaged[Cdecl, Cdecl]<void*, Dtapi.DtVirtualOutData*, byte>, void*, uint>)(int)(*(uint*)(*(int*)pDtMplpOutpChannel + 372)))((nint)pDtMplpOutpChannel, rDtDvc.pDtDvc, (delegate* unmanaged[Cdecl, Cdecl]<void*, Dtapi.DtVirtualOutData*, byte>)global::_003CModule_003E.__unep_0040_003FOnVirtOutData_0040VirtOutDataHandlerHelper_0040DTAPINET_0040_0040_0024_0024FSA_NPAXPAUDtVirtualOutData_0040Dtapi_0040_0040_0040Z, m_pVirtOutDataHandlerHelper);
	}

	public unsafe DTAPI_RESULT GetMplpFifoFree(int FifoIdx, ref int FifoFree)
	{
		Dtapi.DtMplpOutpChannel* pDtMplpOutpChannel = m_pDtMplpOutpChannel;
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num);
		uint result = ((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, int, int*, uint>)(int)(*(uint*)(*(int*)pDtMplpOutpChannel + 376)))((nint)pDtMplpOutpChannel, FifoIdx, &num);
		FifoFree = num;
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetMplpFifoLoad(int FifoIdx, ref int FifoLoad)
	{
		Dtapi.DtMplpOutpChannel* pDtMplpOutpChannel = m_pDtMplpOutpChannel;
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num);
		uint result = ((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, int, int*, uint>)(int)(*(uint*)(*(int*)pDtMplpOutpChannel + 380)))((nint)pDtMplpOutpChannel, FifoIdx, &num);
		FifoLoad = num;
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetMplpFifoSize(int FifoIdx, ref int FifoSize)
	{
		Dtapi.DtMplpOutpChannel* pDtMplpOutpChannel = m_pDtMplpOutpChannel;
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num);
		uint result = ((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, int, int*, uint>)(int)(*(uint*)(*(int*)pDtMplpOutpChannel + 384)))((nint)pDtMplpOutpChannel, FifoIdx, &num);
		FifoSize = num;
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetMplpModStatus(ref DtDvbT2ModStatus MplpModStat1, ref DtDvbT2ModStatus MplpModStat2)
	{
		Dtapi.DtMplpOutpChannel* pDtMplpOutpChannel = m_pDtMplpOutpChannel;
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDvbT2ModStatus dtDvbT2ModStatus);
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDvbT2ModStatus dtDvbT2ModStatus2);
		uint result = ((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, Dtapi.DtDvbT2ModStatus*, Dtapi.DtDvbT2ModStatus*, uint>)(int)(*(uint*)(*(int*)pDtMplpOutpChannel + 388)))((nint)pDtMplpOutpChannel, &dtDvbT2ModStatus, &dtDvbT2ModStatus2);
		MplpModStat1.ConvertFromUnmgd(&dtDvbT2ModStatus);
		MplpModStat2.ConvertFromUnmgd(&dtDvbT2ModStatus2);
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetMplpModStatus(ref DtDvbT2ModStatus MplpModStat)
	{
		Dtapi.DtMplpOutpChannel* pDtMplpOutpChannel = m_pDtMplpOutpChannel;
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDvbT2ModStatus dtDvbT2ModStatus);
		uint result = ((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, Dtapi.DtDvbT2ModStatus*, uint>)(int)(*(uint*)(*(int*)pDtMplpOutpChannel + 392)))((nint)pDtMplpOutpChannel, &dtDvbT2ModStatus);
		MplpModStat.ConvertFromUnmgd(&dtDvbT2ModStatus);
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetMplpModStatus(ref DtDvbC2ModStatus MplpModStat)
	{
		Dtapi.DtMplpOutpChannel* pDtMplpOutpChannel = m_pDtMplpOutpChannel;
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDvbC2ModStatus dtDvbC2ModStatus);
		uint result = ((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, Dtapi.DtDvbC2ModStatus*, uint>)(int)(*(uint*)(*(int*)pDtMplpOutpChannel + 400)))((nint)pDtMplpOutpChannel, &dtDvbC2ModStatus);
		MplpModStat.ConvertFromUnmgd(&dtDvbC2ModStatus);
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT SetMplpChannelModelling([MarshalAs(UnmanagedType.U1)] bool CmEnable, DtCmPars rCmPars, int Channel)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCmPars dtCmPars);
		global::_003CModule_003E.Dtapi_002EDtCmPars_002E_007Bctor_007D(&dtCmPars);
		DTAPI_RESULT result;
		try
		{
			rCmPars.ConvertToUnmgd(&dtCmPars);
			Dtapi.DtMplpOutpChannel* pDtMplpOutpChannel = m_pDtMplpOutpChannel;
			result = (DTAPI_RESULT)((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, byte, Dtapi.DtCmPars*, int, uint>)(int)(*(uint*)(*(int*)pDtMplpOutpChannel + 404)))((nint)pDtMplpOutpChannel, CmEnable ? ((byte)1) : ((byte)0), &dtCmPars, Channel);
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

	public unsafe DTAPI_RESULT SetTimeInfo(DtTimeOfDay rTimeInfo)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtTimeOfDay dtTimeOfDay);
		global::_003CModule_003E.Dtapi_002EDtTimeOfDay_002E_007Bctor_007D(&dtTimeOfDay, 0u, 0u);
		*(uint*)(&dtTimeOfDay) = rTimeInfo.m_Seconds;
		System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtTimeOfDay, uint>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtTimeOfDay, 4)) = rTimeInfo.m_Nanoseconds;
		Dtapi.DtMplpOutpChannel* pDtMplpOutpChannel = m_pDtMplpOutpChannel;
		return (DTAPI_RESULT)((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, Dtapi.DtTimeOfDay, uint>)(int)(*(uint*)(*(int*)pDtMplpOutpChannel + 408)))((nint)pDtMplpOutpChannel, dtTimeOfDay);
	}

	public unsafe DTAPI_RESULT WriteMplp(int FifoIdx, byte[] rBuffer, int NumBytesToWrite)
	{
		fixed (byte* ptr = &rBuffer[0])
		{
			Dtapi.DtMplpOutpChannel* pDtMplpOutpChannel = m_pDtMplpOutpChannel;
			return (DTAPI_RESULT)((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, int, sbyte*, int, uint>)(int)(*(uint*)(*(int*)pDtMplpOutpChannel + 412)))((nint)pDtMplpOutpChannel, FifoIdx, (sbyte*)ptr, NumBytesToWrite);
		}
	}

	public unsafe DTAPI_RESULT WriteMplpPacket(int FifoIdx, byte[] rPacket, int PacketSize, DtFractionInt Duration)
	{
		fixed (byte* ptr = &rPacket[0])
		{
			System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtFractionInt dtFractionInt);
			*(int*)(&dtFractionInt) = Duration.m_Num;
			System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtFractionInt, int>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtFractionInt, 4)) = Duration.m_Den;
			Dtapi.DtMplpOutpChannel* pDtMplpOutpChannel = m_pDtMplpOutpChannel;
			return (DTAPI_RESULT)((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, int, sbyte*, int, Dtapi.DtFractionInt, uint>)(int)(*(uint*)(*(int*)pDtMplpOutpChannel + 416)))((nint)pDtMplpOutpChannel, FifoIdx, (sbyte*)ptr, PacketSize, dtFractionInt);
		}
	}

	public unsafe DTAPI_RESULT WriteMplpPacket(int FifoIdx, byte[] rPacket, int PacketSize)
	{
		fixed (byte* ptr = &rPacket[0])
		{
			Dtapi.DtMplpOutpChannel* pDtMplpOutpChannel = m_pDtMplpOutpChannel;
			return (DTAPI_RESULT)((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, int, sbyte*, int, uint>)(int)(*(uint*)(*(int*)pDtMplpOutpChannel + 420)))((nint)pDtMplpOutpChannel, FifoIdx, (sbyte*)ptr, PacketSize);
		}
	}

	public unsafe DtMplpOutpChannel()
	{
		Dtapi.DtMplpOutpChannel* ptr = (Dtapi.DtMplpOutpChannel*)global::_003CModule_003E.@new(336u);
		Dtapi.DtMplpOutpChannel* ptr2;
		try
		{
			ptr2 = ((ptr == null) ? null : global::_003CModule_003E.Dtapi_002EDtMplpOutpChannel_002E_007Bctor_007D(ptr));
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.delete(ptr, 336u);
			throw;
		}
		((object)this)._002Ector();
		m_pDtOutpChannel = (Dtapi.DtOutpChannel*)ptr2;
		m_pDtOutpChannelDerived = ptr2;
		try
		{
			m_pDtMplpOutpChannel = (Dtapi.DtMplpOutpChannel*)m_pDtOutpChannelDerived;
			VirtOutDataHandlerHelper* ptr3 = (VirtOutDataHandlerHelper*)global::_003CModule_003E.@new(4u);
			VirtOutDataHandlerHelper* pVirtOutDataHandlerHelper;
			try
			{
				if (ptr3 != null)
				{
					*(int*)ptr3 = (int)((IntPtr)GCHandle.Alloc(this)).ToPointer();
					pVirtOutDataHandlerHelper = ptr3;
				}
				else
				{
					pVirtOutDataHandlerHelper = null;
				}
			}
			catch
			{
				//try-fault
				global::_003CModule_003E.delete(ptr3, 4u);
				throw;
			}
			m_pVirtOutDataHandlerHelper = pVirtOutDataHandlerHelper;
			return;
		}
		catch
		{
			//try-fault
			base.Dispose(A_0: true);
			throw;
		}
	}

	private void _007EDtMplpOutpChannel()
	{
		_0021DtMplpOutpChannel();
	}

	private unsafe void _0021DtMplpOutpChannel()
	{
		Dtapi.DtMplpOutpChannel* pDtMplpOutpChannel = m_pDtMplpOutpChannel;
		if (pDtMplpOutpChannel != null)
		{
			Dtapi.DtMplpOutpChannel* ptr = pDtMplpOutpChannel;
			((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, uint, void*>)(int)(*(uint*)(int)(*(uint*)ptr)))((nint)ptr, 1u);
			m_pDtMplpOutpChannel = null;
		}
		VirtOutDataHandlerHelper* pVirtOutDataHandlerHelper = m_pVirtOutDataHandlerHelper;
		if (pVirtOutDataHandlerHelper != null)
		{
			VirtOutDataHandlerHelper* ptr2 = pVirtOutDataHandlerHelper;
			global::_003CModule_003E.gcroot_003CDTAPINET_003A_003ADtMplpOutpChannel_0020_005E_003E_002E_007Bdtor_007D((gcroot_003CDTAPINET_003A_003ADtMplpOutpChannel_0020_005E_003E*)ptr2);
			global::_003CModule_003E.delete(ptr2, 4u);
			m_pVirtOutDataHandlerHelper = null;
		}
	}

	[HandleProcessCorruptedStateExceptions]
	protected override void Dispose([MarshalAs(UnmanagedType.U1)] bool A_0)
	{
		if (A_0)
		{
			try
			{
				_0021DtMplpOutpChannel();
				return;
			}
			finally
			{
				base.Dispose(A_0: true);
			}
		}
		try
		{
			_0021DtMplpOutpChannel();
		}
		finally
		{
			base.Dispose(A_0: false);
		}
	}
}
