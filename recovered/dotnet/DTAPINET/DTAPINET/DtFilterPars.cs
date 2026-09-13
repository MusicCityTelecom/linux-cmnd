using System.Collections.Generic;
using System.Runtime.CompilerServices;
using Dtapi;
using std;

namespace DTAPINET;

public class DtFilterPars
{
	public List<DtFiltCoeff> m_FiltCoeffs;

	internal unsafe void ConvertToUnmgd(Dtapi.DtFilterPars* uFilterPars)
	{
		((int*)uFilterPars)[1] = *(int*)uFilterPars;
		int num = 0;
		if (0 < m_FiltCoeffs.Count)
		{
			System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtFiltCoeff dtFiltCoeff);
			do
			{
				*(int*)(&dtFiltCoeff) = m_FiltCoeffs[num].m_TapIdx;
				System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtFiltCoeff, double>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtFiltCoeff, 8)) = m_FiltCoeffs[num].m_Coeff;
				global::_003CModule_003E.std_002Evector_003CDtapi_003A_003ADtFiltCoeff_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtFiltCoeff_003E_0020_003E_002Eemplace_back_003Cstruct_0020Dtapi_003A_003ADtFiltCoeff_0020const_0020_0026_003E((vector_003CDtapi_003A_003ADtFiltCoeff_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtFiltCoeff_003E_0020_003E*)uFilterPars, &dtFiltCoeff);
				num++;
			}
			while (num < m_FiltCoeffs.Count);
		}
	}

	internal unsafe void ConvertFromUnmgd(Dtapi.DtFilterPars* uFilterPars)
	{
		m_FiltCoeffs.Clear();
		int num = 0;
		if (0 < ((((int*)uFilterPars)[1] - *(int*)uFilterPars) & -16))
		{
			int num2 = 0;
			do
			{
				DtFiltCoeff dtFiltCoeff = new DtFiltCoeff();
				dtFiltCoeff.m_TapIdx = *(int*)(*(int*)uFilterPars + num2);
				dtFiltCoeff.m_Coeff = *(double*)(num2 + *(int*)uFilterPars + 8);
				m_FiltCoeffs.Add(dtFiltCoeff);
				num++;
				num2 += 16;
			}
			while (num < ((int*)uFilterPars)[1] - *(int*)uFilterPars >> 4);
		}
	}

	public unsafe DtFilterPars()
	{
		m_FiltCoeffs = new List<DtFiltCoeff>();
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtFilterPars dtFilterPars);
		*(int*)(&dtFilterPars) = 0;
		System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtFilterPars, int>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtFilterPars, 4)) = 0;
		System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtFilterPars, int>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtFilterPars, 8)) = 0;
		try
		{
			ConvertFromUnmgd(&dtFilterPars);
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtFilterPars*, void>)(&global::_003CModule_003E.Dtapi_002EDtFilterPars_002E_007Bdtor_007D), &dtFilterPars);
			throw;
		}
		global::_003CModule_003E.std_002Evector_003CDtapi_003A_003ADtFiltCoeff_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtFiltCoeff_003E_0020_003E_002E_Tidy((vector_003CDtapi_003A_003ADtFiltCoeff_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtFiltCoeff_003E_0020_003E*)(&dtFilterPars));
	}
}
