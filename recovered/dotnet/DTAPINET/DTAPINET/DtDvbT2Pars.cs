using System;
using System.Runtime.CompilerServices;
using System.Runtime.InteropServices;
using Dtapi;

namespace DTAPINET;

public class DtDvbT2Pars : DtDvbT2ComponentPars
{
	public DtVirtualOutPars m_VirtOutput;

	public DtDvbT2MiPars m_T2Mi;

	public int m_NumFefComponents;

	public DtDvbT2ComponentPars[] m_FefComponent;

	public new unsafe void Init()
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDvbT2Pars dtDvbT2Pars);
		global::_003CModule_003E.Dtapi_002EDtDvbT2Pars_002E_007Bctor_007D(&dtDvbT2Pars);
		try
		{
			((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, void>)(int)(*(uint*)(*(int*)(&dtDvbT2Pars) + 4)))((nint)(&dtDvbT2Pars));
			ConvertFromUnmgd(&dtDvbT2Pars);
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtDvbT2Pars*, void>)(&global::_003CModule_003E.Dtapi_002EDtDvbT2Pars_002E_007Bdtor_007D), &dtDvbT2Pars);
			throw;
		}
		global::_003CModule_003E.Dtapi_002EDtDvbT2Pars_002E_007Bdtor_007D(&dtDvbT2Pars);
	}

	public unsafe DTAPI_RESULT CheckValidity()
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDvbT2Pars dtDvbT2Pars);
		global::_003CModule_003E.Dtapi_002EDtDvbT2Pars_002E_007Bctor_007D(&dtDvbT2Pars);
		DTAPI_RESULT result;
		try
		{
			ConvertToUnmgd(&dtDvbT2Pars);
			result = (DTAPI_RESULT)((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, uint>)(int)(*(uint*)(*(int*)(&dtDvbT2Pars) + 24)))((nint)(&dtDvbT2Pars));
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

	public unsafe DTAPI_RESULT ComputeTDesign()
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDvbT2Pars dtDvbT2Pars);
		global::_003CModule_003E.Dtapi_002EDtDvbT2Pars_002E_007Bctor_007D(&dtDvbT2Pars);
		uint result;
		try
		{
			ConvertToUnmgd(&dtDvbT2Pars);
			result = ((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, uint>)(int)(*(uint*)(*(int*)(&dtDvbT2Pars) + 28)))((nint)(&dtDvbT2Pars));
			ConvertFromUnmgd(&dtDvbT2Pars);
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtDvbT2Pars*, void>)(&global::_003CModule_003E.Dtapi_002EDtDvbT2Pars_002E_007Bdtor_007D), &dtDvbT2Pars);
			throw;
		}
		global::_003CModule_003E.Dtapi_002EDtDvbT2Pars_002E_007Bdtor_007D(&dtDvbT2Pars);
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetParamInfo(ref DtDvbT2ParamInfo ParamInfo1, ref DtDvbT2ParamInfo ParamInfo2)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDvbT2Pars dtDvbT2Pars);
		global::_003CModule_003E.Dtapi_002EDtDvbT2Pars_002E_007Bctor_007D(&dtDvbT2Pars);
		uint result;
		try
		{
			ConvertToUnmgd(&dtDvbT2Pars);
			System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDvbT2ParamInfo dtDvbT2ParamInfo);
			System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDvbT2ParamInfo dtDvbT2ParamInfo2);
			result = ((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, Dtapi.DtDvbT2ParamInfo*, Dtapi.DtDvbT2ParamInfo*, uint>)(int)(*(uint*)(*(int*)(&dtDvbT2Pars) + 32)))((nint)(&dtDvbT2Pars), &dtDvbT2ParamInfo, &dtDvbT2ParamInfo2);
			ParamInfo1.ConvertFromUnmgd(&dtDvbT2ParamInfo);
			ParamInfo1.ConvertFromUnmgd(&dtDvbT2ParamInfo2);
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtDvbT2Pars*, void>)(&global::_003CModule_003E.Dtapi_002EDtDvbT2Pars_002E_007Bdtor_007D), &dtDvbT2Pars);
			throw;
		}
		global::_003CModule_003E.Dtapi_002EDtDvbT2Pars_002E_007Bdtor_007D(&dtDvbT2Pars);
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT GetParamInfo(ref DtDvbT2ParamInfo ParamInfo)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDvbT2Pars dtDvbT2Pars);
		global::_003CModule_003E.Dtapi_002EDtDvbT2Pars_002E_007Bctor_007D(&dtDvbT2Pars);
		uint result;
		try
		{
			ConvertToUnmgd(&dtDvbT2Pars);
			System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDvbT2ParamInfo dtDvbT2ParamInfo);
			result = ((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, Dtapi.DtDvbT2ParamInfo*, uint>)(int)(*(uint*)(*(int*)(&dtDvbT2Pars) + 36)))((nint)(&dtDvbT2Pars), &dtDvbT2ParamInfo);
			ParamInfo.ConvertFromUnmgd(&dtDvbT2ParamInfo);
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtDvbT2Pars*, void>)(&global::_003CModule_003E.Dtapi_002EDtDvbT2Pars_002E_007Bdtor_007D), &dtDvbT2Pars);
			throw;
		}
		global::_003CModule_003E.Dtapi_002EDtDvbT2Pars_002E_007Bdtor_007D(&dtDvbT2Pars);
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT OptimisePlpNumBlocks(ref DtDvbT2ParamInfo ParamInfo, ref int OptPlpNumBlocks, ref int OptNumDataSyms)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDvbT2Pars dtDvbT2Pars);
		global::_003CModule_003E.Dtapi_002EDtDvbT2Pars_002E_007Bctor_007D(&dtDvbT2Pars);
		uint result;
		try
		{
			ConvertToUnmgd(&dtDvbT2Pars);
			int num = OptPlpNumBlocks;
			int num2 = OptNumDataSyms;
			System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDvbT2ParamInfo dtDvbT2ParamInfo);
			result = global::_003CModule_003E.Dtapi_002EDtDvbT2Pars_002EOptimisePlpNumBlocks(&dtDvbT2Pars, &dtDvbT2ParamInfo, &num, &num2);
			ParamInfo.ConvertFromUnmgd(&dtDvbT2ParamInfo);
			OptPlpNumBlocks = num;
			OptNumDataSyms = num2;
			ConvertFromUnmgd(&dtDvbT2Pars);
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtDvbT2Pars*, void>)(&global::_003CModule_003E.Dtapi_002EDtDvbT2Pars_002E_007Bdtor_007D), &dtDvbT2Pars);
			throw;
		}
		global::_003CModule_003E.Dtapi_002EDtDvbT2Pars_002E_007Bdtor_007D(&dtDvbT2Pars);
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT OptimisePlpNumBlocks(ref DtDvbT2ParamInfo ParamInfo, ref int OptPlpNumBlocks)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDvbT2Pars dtDvbT2Pars);
		global::_003CModule_003E.Dtapi_002EDtDvbT2Pars_002E_007Bctor_007D(&dtDvbT2Pars);
		uint result;
		try
		{
			ConvertToUnmgd(&dtDvbT2Pars);
			int num = OptPlpNumBlocks;
			System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDvbT2ParamInfo dtDvbT2ParamInfo);
			result = global::_003CModule_003E.Dtapi_002EDtDvbT2Pars_002EOptimisePlpNumBlocks(&dtDvbT2Pars, &dtDvbT2ParamInfo, &num);
			ParamInfo.ConvertFromUnmgd(&dtDvbT2ParamInfo);
			OptPlpNumBlocks = num;
			ConvertFromUnmgd(&dtDvbT2Pars);
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtDvbT2Pars*, void>)(&global::_003CModule_003E.Dtapi_002EDtDvbT2Pars_002E_007Bdtor_007D), &dtDvbT2Pars);
			throw;
		}
		global::_003CModule_003E.Dtapi_002EDtDvbT2Pars_002E_007Bdtor_007D(&dtDvbT2Pars);
		return (DTAPI_RESULT)result;
	}

	[return: MarshalAs(UnmanagedType.U1)]
	public unsafe bool operator ==(DtDvbT2Pars T2Pars)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDvbT2Pars dtDvbT2Pars);
		global::_003CModule_003E.Dtapi_002EDtDvbT2Pars_002E_007Bctor_007D(&dtDvbT2Pars);
		bool result;
		try
		{
			System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDvbT2Pars dtDvbT2Pars2);
			global::_003CModule_003E.Dtapi_002EDtDvbT2Pars_002E_007Bctor_007D(&dtDvbT2Pars2);
			try
			{
				ConvertToUnmgd(&dtDvbT2Pars);
				T2Pars.ConvertToUnmgd(&dtDvbT2Pars2);
				result = ((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, Dtapi.DtDvbT2Pars*, byte>)(int)(*(uint*)(*(int*)(&dtDvbT2Pars) + 40)))((nint)(&dtDvbT2Pars), &dtDvbT2Pars2) != 0;
			}
			catch
			{
				//try-fault
				global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtDvbT2Pars*, void>)(&global::_003CModule_003E.Dtapi_002EDtDvbT2Pars_002E_007Bdtor_007D), &dtDvbT2Pars2);
				throw;
			}
			global::_003CModule_003E.Dtapi_002EDtDvbT2Pars_002E_007Bdtor_007D(&dtDvbT2Pars2);
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

	[return: MarshalAs(UnmanagedType.U1)]
	public bool operator !=(DtDvbT2Pars T2Pars)
	{
		return !op_Equality(T2Pars);
	}

	[return: MarshalAs(UnmanagedType.U1)]
	public unsafe bool IsEqual(DtDvbT2Pars T2Pars, int CompareMode)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDvbT2Pars dtDvbT2Pars);
		global::_003CModule_003E.Dtapi_002EDtDvbT2Pars_002E_007Bctor_007D(&dtDvbT2Pars);
		bool result;
		try
		{
			System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDvbT2Pars dtDvbT2Pars2);
			global::_003CModule_003E.Dtapi_002EDtDvbT2Pars_002E_007Bctor_007D(&dtDvbT2Pars2);
			try
			{
				ConvertToUnmgd(&dtDvbT2Pars);
				T2Pars.ConvertToUnmgd(&dtDvbT2Pars2);
				result = ((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, Dtapi.DtDvbT2Pars*, int, byte>)(int)(*(uint*)(*(int*)(&dtDvbT2Pars) + 48)))((nint)(&dtDvbT2Pars), &dtDvbT2Pars2, CompareMode) != 0;
			}
			catch
			{
				//try-fault
				global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtDvbT2Pars*, void>)(&global::_003CModule_003E.Dtapi_002EDtDvbT2Pars_002E_007Bdtor_007D), &dtDvbT2Pars2);
				throw;
			}
			global::_003CModule_003E.Dtapi_002EDtDvbT2Pars_002E_007Bdtor_007D(&dtDvbT2Pars2);
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

	internal unsafe void ConvertToUnmgd(Dtapi.DtDvbT2Pars* rT2Pars)
	{
		((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, void>)(int)(*(uint*)(*(int*)rT2Pars + 4)))((nint)rT2Pars);
		ConvertToUnmgd((Dtapi.DtDvbT2ComponentPars*)rT2Pars);
		((int*)rT2Pars)[106] = m_NumFefComponents;
		int num = 0;
		if (0 < m_NumFefComponents)
		{
			Dtapi.DtDvbT2Pars* ptr = (Dtapi.DtDvbT2Pars*)((byte*)rT2Pars + 432);
			do
			{
				m_FefComponent[num].ConvertToUnmgd((Dtapi.DtDvbT2ComponentPars*)ptr);
				num++;
				ptr = (Dtapi.DtDvbT2Pars*)((byte*)ptr + 344);
			}
			while (num < m_NumFefComponents);
		}
		m_T2Mi.ConvertToUnmgd((Dtapi.DtDvbT2MiPars*)((byte*)rT2Pars + 360));
		m_VirtOutput.ConvertToUnmgd((Dtapi.DtVirtualOutPars*)((byte*)rT2Pars + 344));
	}

	internal unsafe void ConvertFromUnmgd(Dtapi.DtDvbT2Pars* rT2Pars)
	{
		ConvertFromUnmgd((Dtapi.DtDvbT2ComponentPars*)rT2Pars);
		m_NumFefComponents = ((int*)rT2Pars)[106];
		int num = 0;
		if (0 < (nint)m_FefComponent.LongLength)
		{
			do
			{
				m_FefComponent[num].Init();
				num++;
			}
			while (num < (nint)m_FefComponent.LongLength);
		}
		int num2 = 0;
		if (0 < m_NumFefComponents)
		{
			Dtapi.DtDvbT2Pars* ptr = (Dtapi.DtDvbT2Pars*)((byte*)rT2Pars + 432);
			do
			{
				m_FefComponent[num2].ConvertFromUnmgd((Dtapi.DtDvbT2ComponentPars*)ptr);
				num2++;
				ptr = (Dtapi.DtDvbT2Pars*)((byte*)ptr + 344);
			}
			while (num2 < m_NumFefComponents);
		}
		m_T2Mi.ConvertFromUnmgd((Dtapi.DtDvbT2MiPars*)((byte*)rT2Pars + 360));
		m_VirtOutput.ConvertFromUnmgd((Dtapi.DtVirtualOutPars*)((byte*)rT2Pars + 344));
	}

	public DtDvbT2Pars()
	{
		m_NumFefComponents = 0;
		m_FefComponent = new DtDvbT2ComponentPars[1];
		m_FefComponent[0] = new DtDvbT2ComponentPars();
		m_T2Mi = new DtDvbT2MiPars();
		m_VirtOutput = new DtVirtualOutPars();
		Init();
	}
}
