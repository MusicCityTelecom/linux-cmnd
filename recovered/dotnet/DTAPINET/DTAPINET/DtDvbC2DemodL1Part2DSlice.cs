using System.Collections.Generic;
using System.Runtime.CompilerServices;
using Dtapi;
using std;

namespace DTAPINET;

public class DtDvbC2DemodL1Part2DSlice
{
	public int m_Id;

	public int m_TunePosition;

	public int m_OffsetLeft;

	public int m_OffsetRight;

	public int m_TiDepth;

	public int m_Type;

	public int m_FecHdrType;

	public int m_ConstConfig;

	public int m_LeftNotch;

	public int m_NumPlps;

	public List<DtDvbC2DemodL1Part2Plp> m_Plps;

	internal unsafe void ConvertToUnmgd(Dtapi.DtDvbC2DemodL1Part2DSlice* uC2L1Pars)
	{
		*(int*)uC2L1Pars = m_Id;
		((int*)uC2L1Pars)[1] = m_TunePosition;
		((int*)uC2L1Pars)[2] = m_OffsetLeft;
		((int*)uC2L1Pars)[3] = m_OffsetRight;
		((int*)uC2L1Pars)[4] = m_TiDepth;
		((int*)uC2L1Pars)[5] = m_Type;
		((int*)uC2L1Pars)[6] = m_FecHdrType;
		((int*)uC2L1Pars)[7] = m_ConstConfig;
		((int*)uC2L1Pars)[8] = m_LeftNotch;
		((int*)uC2L1Pars)[9] = m_NumPlps;
		Dtapi.DtDvbC2DemodL1Part2DSlice* ptr = (Dtapi.DtDvbC2DemodL1Part2DSlice*)((byte*)uC2L1Pars + 40);
		vector_003CDtapi_003A_003ADtDvbC2DemodL1Part2Plp_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2DemodL1Part2Plp_003E_0020_003E* ptr2 = (vector_003CDtapi_003A_003ADtDvbC2DemodL1Part2Plp_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2DemodL1Part2Plp_003E_0020_003E*)ptr;
		((int*)ptr2)[1] = *(int*)ptr2;
		int num = 0;
		if (0 < m_Plps.Count)
		{
			System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDvbC2DemodL1Part2Plp dtDvbC2DemodL1Part2Plp);
			do
			{
				global::_003CModule_003E.Dtapi_002EDtDvbC2DemodL1Part2Plp_002E_007Bctor_007D(&dtDvbC2DemodL1Part2Plp);
				m_Plps[num].ConvertToUnmgd(&dtDvbC2DemodL1Part2Plp);
				global::_003CModule_003E.std_002Evector_003CDtapi_003A_003ADtDvbC2DemodL1Part2Plp_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2DemodL1Part2Plp_003E_0020_003E_002Eemplace_back_003Cstruct_0020Dtapi_003A_003ADtDvbC2DemodL1Part2Plp_0020const_0020_0026_003E((vector_003CDtapi_003A_003ADtDvbC2DemodL1Part2Plp_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2DemodL1Part2Plp_003E_0020_003E*)ptr, &dtDvbC2DemodL1Part2Plp);
				num++;
			}
			while (num < m_Plps.Count);
		}
	}

	internal unsafe void ConvertFromUnmgd(Dtapi.DtDvbC2DemodL1Part2DSlice* uC2L1Pars)
	{
		m_Id = *(int*)uC2L1Pars;
		m_TunePosition = ((int*)uC2L1Pars)[1];
		m_OffsetLeft = ((int*)uC2L1Pars)[2];
		m_OffsetRight = ((int*)uC2L1Pars)[3];
		m_TiDepth = ((int*)uC2L1Pars)[4];
		m_Type = ((int*)uC2L1Pars)[5];
		m_FecHdrType = ((int*)uC2L1Pars)[6];
		m_ConstConfig = ((int*)uC2L1Pars)[7];
		m_LeftNotch = ((int*)uC2L1Pars)[8];
		m_NumPlps = ((int*)uC2L1Pars)[9];
		m_Plps.Clear();
		uint num = 0u;
		vector_003CDtapi_003A_003ADtDvbC2DemodL1Part2Plp_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2DemodL1Part2Plp_003E_0020_003E* ptr = (vector_003CDtapi_003A_003ADtDvbC2DemodL1Part2Plp_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2DemodL1Part2Plp_003E_0020_003E*)((byte*)uC2L1Pars + 40);
		if (0u < (uint)((((int*)ptr)[1] - *(int*)ptr) / 48))
		{
			ptr = (vector_003CDtapi_003A_003ADtDvbC2DemodL1Part2Plp_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2DemodL1Part2Plp_003E_0020_003E*)((byte*)uC2L1Pars + 40);
			vector_003CDtapi_003A_003ADtDvbC2DemodL1Part2Plp_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2DemodL1Part2Plp_003E_0020_003E* ptr2 = (vector_003CDtapi_003A_003ADtDvbC2DemodL1Part2Plp_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2DemodL1Part2Plp_003E_0020_003E*)((byte*)ptr + 4);
			int num2 = 0;
			do
			{
				DtDvbC2DemodL1Part2Plp dtDvbC2DemodL1Part2Plp = new DtDvbC2DemodL1Part2Plp();
				dtDvbC2DemodL1Part2Plp.ConvertFromUnmgd((Dtapi.DtDvbC2DemodL1Part2Plp*)(num2 + ((int*)uC2L1Pars)[10]));
				m_Plps.Add(dtDvbC2DemodL1Part2Plp);
				num++;
				num2 += 48;
			}
			while (num < (uint)((*(int*)ptr2 - *(int*)ptr) / 48));
		}
	}

	public unsafe DtDvbC2DemodL1Part2DSlice()
	{
		m_Plps = new List<DtDvbC2DemodL1Part2Plp>();
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDvbC2DemodL1Part2DSlice dtDvbC2DemodL1Part2DSlice);
		global::_003CModule_003E.Dtapi_002EDtDvbC2DemodL1Part2DSlice_002E_007Bctor_007D(&dtDvbC2DemodL1Part2DSlice);
		try
		{
			ConvertFromUnmgd(&dtDvbC2DemodL1Part2DSlice);
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtDvbC2DemodL1Part2DSlice*, void>)(&global::_003CModule_003E.Dtapi_002EDtDvbC2DemodL1Part2DSlice_002E_007Bdtor_007D), &dtDvbC2DemodL1Part2DSlice);
			throw;
		}
		global::_003CModule_003E.std_002Evector_003CDtapi_003A_003ADtDvbC2DemodL1Part2Plp_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2DemodL1Part2Plp_003E_0020_003E_002E_Tidy((vector_003CDtapi_003A_003ADtDvbC2DemodL1Part2Plp_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2DemodL1Part2Plp_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtDvbC2DemodL1Part2DSlice, 40)));
	}
}
