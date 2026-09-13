using System.Runtime.CompilerServices;
using System.Runtime.InteropServices;
using Dtapi;

namespace DTAPINET;

public class DtIsdbTmmPars
{
	public int m_Bandwidth;

	public int m_SubChannel;

	public int m_NumTss;

	public DtVirtualOutPars m_VirtOutput;

	public DtIsdbtPars[] m_Tss;

	public DtPlpInpPars[] m_TsInputs;

	public unsafe DTAPI_RESULT CheckValidity()
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtIsdbTmmPars dtIsdbTmmPars);
		global::_003CModule_003E.Dtapi_002EDtIsdbTmmPars_002E_007Bctor_007D(&dtIsdbTmmPars);
		DTAPI_RESULT result;
		try
		{
			ConvertToUnmgd(&dtIsdbTmmPars);
			result = (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtIsdbTmmPars_002ECheckValidity(&dtIsdbTmmPars);
			ConvertFromUnmgd(&dtIsdbTmmPars);
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

	public unsafe void Init()
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtIsdbTmmPars dtIsdbTmmPars);
		global::_003CModule_003E.Dtapi_002EDtIsdbTmmPars_002E_007Bctor_007D(&dtIsdbTmmPars);
		try
		{
			ConvertToUnmgd(&dtIsdbTmmPars);
			global::_003CModule_003E.Dtapi_002EDtIsdbTmmPars_002ECheckValidity(&dtIsdbTmmPars);
			ConvertFromUnmgd(&dtIsdbTmmPars);
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtIsdbTmmPars*, void>)(&global::_003CModule_003E.Dtapi_002EDtIsdbTmmPars_002E_007Bdtor_007D), &dtIsdbTmmPars);
			throw;
		}
		global::_003CModule_003E.Dtapi_002EDtIsdbTmmPars_002E_007Bdtor_007D(&dtIsdbTmmPars);
	}

	public unsafe int NumSegm()
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtIsdbTmmPars dtIsdbTmmPars);
		global::_003CModule_003E.Dtapi_002EDtIsdbTmmPars_002E_007Bctor_007D(&dtIsdbTmmPars);
		int result;
		try
		{
			ConvertToUnmgd(&dtIsdbTmmPars);
			result = global::_003CModule_003E.Dtapi_002EDtIsdbTmmPars_002ENumSegm(&dtIsdbTmmPars);
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

	[return: MarshalAs(UnmanagedType.U1)]
	public unsafe bool operator ==(DtIsdbTmmPars Rhs)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtIsdbTmmPars dtIsdbTmmPars);
		global::_003CModule_003E.Dtapi_002EDtIsdbTmmPars_002E_007Bctor_007D(&dtIsdbTmmPars);
		bool result;
		try
		{
			ConvertToUnmgd(&dtIsdbTmmPars);
			System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtIsdbTmmPars dtIsdbTmmPars2);
			global::_003CModule_003E.Dtapi_002EDtIsdbTmmPars_002E_007Bctor_007D(&dtIsdbTmmPars2);
			try
			{
				Rhs.ConvertToUnmgd(&dtIsdbTmmPars2);
				result = global::_003CModule_003E.Dtapi_002EDtIsdbTmmPars_002E_003D_003D(&dtIsdbTmmPars, &dtIsdbTmmPars2);
			}
			catch
			{
				//try-fault
				global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtIsdbTmmPars*, void>)(&global::_003CModule_003E.Dtapi_002EDtIsdbTmmPars_002E_007Bdtor_007D), &dtIsdbTmmPars2);
				throw;
			}
			global::_003CModule_003E.Dtapi_002EDtIsdbTmmPars_002E_007Bdtor_007D(&dtIsdbTmmPars2);
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

	[return: MarshalAs(UnmanagedType.U1)]
	public bool operator !=(DtIsdbTmmPars Rhs)
	{
		return !op_Equality(Rhs);
	}

	internal unsafe void ConvertToUnmgd(Dtapi.DtIsdbTmmPars* uIsdbTmmPars)
	{
		*(int*)uIsdbTmmPars = m_Bandwidth;
		((int*)uIsdbTmmPars)[1] = m_SubChannel;
		m_VirtOutput.ConvertToUnmgd((Dtapi.DtVirtualOutPars*)((byte*)uIsdbTmmPars + 16));
		((int*)uIsdbTmmPars)[2] = m_NumTss;
		int num = 0;
		if (0 < m_NumTss)
		{
			Dtapi.DtIsdbTmmPars* ptr = (Dtapi.DtIsdbTmmPars*)((byte*)uIsdbTmmPars + 1656);
			Dtapi.DtIsdbTmmPars* ptr2 = (Dtapi.DtIsdbTmmPars*)((byte*)uIsdbTmmPars + 32);
			do
			{
				m_Tss[num].ConvertToUnmgd((Dtapi.DtIsdbtPars*)ptr2);
				m_TsInputs[num].ConvertToUnmgd((Dtapi.DtPlpInpPars*)ptr);
				num++;
				ptr2 = (Dtapi.DtIsdbTmmPars*)((byte*)ptr2 + 116);
				ptr = (Dtapi.DtIsdbTmmPars*)((byte*)ptr + 216);
			}
			while (num < m_NumTss);
		}
	}

	internal unsafe void ConvertFromUnmgd(Dtapi.DtIsdbTmmPars* uIsdbTmmPars)
	{
		m_Bandwidth = *(int*)uIsdbTmmPars;
		m_SubChannel = ((int*)uIsdbTmmPars)[1];
		m_VirtOutput.ConvertFromUnmgd((Dtapi.DtVirtualOutPars*)((byte*)uIsdbTmmPars + 16));
		int num = (m_NumTss = ((int*)uIsdbTmmPars)[2]);
		int num2 = 0;
		if (0 < num)
		{
			Dtapi.DtIsdbTmmPars* ptr = (Dtapi.DtIsdbTmmPars*)((byte*)uIsdbTmmPars + 1656);
			Dtapi.DtIsdbTmmPars* ptr2 = (Dtapi.DtIsdbTmmPars*)((byte*)uIsdbTmmPars + 32);
			do
			{
				m_Tss[num2].ConvertFromUnmgd((Dtapi.DtIsdbtPars*)ptr2);
				m_TsInputs[num2].ConvertFromUnmgd((Dtapi.DtPlpInpPars*)ptr);
				num2++;
				ptr2 = (Dtapi.DtIsdbTmmPars*)((byte*)ptr2 + 116);
				ptr = (Dtapi.DtIsdbTmmPars*)((byte*)ptr + 216);
			}
			while (num2 < m_NumTss);
		}
	}

	public unsafe DtIsdbTmmPars()
	{
		m_VirtOutput = new DtVirtualOutPars();
		m_Tss = new DtIsdbtPars[14];
		m_TsInputs = new DtPlpInpPars[14];
		int num = 0;
		do
		{
			m_Tss[num] = new DtIsdbtPars();
			m_TsInputs[num] = new DtPlpInpPars();
			num++;
		}
		while (num < 14);
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtIsdbTmmPars dtIsdbTmmPars);
		global::_003CModule_003E.Dtapi_002EDtIsdbTmmPars_002E_007Bctor_007D(&dtIsdbTmmPars);
		try
		{
			ConvertFromUnmgd(&dtIsdbTmmPars);
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtIsdbTmmPars*, void>)(&global::_003CModule_003E.Dtapi_002EDtIsdbTmmPars_002E_007Bdtor_007D), &dtIsdbTmmPars);
			throw;
		}
		global::_003CModule_003E.Dtapi_002EDtIsdbTmmPars_002E_007Bdtor_007D(&dtIsdbTmmPars);
	}
}
