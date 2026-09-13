using System.Collections.Generic;
using System.Runtime.CompilerServices;
using Dtapi;
using std;

namespace DTAPINET;

public class DtDvbC2PlpPars
{
	public bool m_Hem;

	public bool m_Npd;

	public int m_Issy;

	public int m_IssyBufs;

	public int m_IssyOutputDelay;

	public int m_TsRate;

	public int m_Ccm;

	public int m_Id;

	public bool m_Bundled;

	public int m_Type;

	public int m_GroupId;

	public int m_FecType;

	public int m_CodeRate;

	public int m_Modulation;

	public int m_HdrCntr;

	public List<DtDvbC2XFecFrameHeader> m_AcmHeaders;

	public bool m_PsiSiReproc;

	public int m_TsId;

	public int m_OnwId;

	public bool m_NoData;

	public DtDvbC2PlpPars()
	{
		m_AcmHeaders = new List<DtDvbC2XFecFrameHeader>();
		Init(0);
	}

	public unsafe void Init(int PlpId)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDvbC2PlpPars dtDvbC2PlpPars);
		System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtDvbC2PlpPars, int>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtDvbC2PlpPars, 64)) = 0;
		System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtDvbC2PlpPars, int>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtDvbC2PlpPars, 68)) = 0;
		System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtDvbC2PlpPars, int>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtDvbC2PlpPars, 72)) = 0;
		try
		{
			global::_003CModule_003E.Dtapi_002EDtDvbC2PlpPars_002EInit(&dtDvbC2PlpPars, PlpId);
			ConvertFromUnmgd(&dtDvbC2PlpPars);
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtDvbC2PlpPars*, void>)(&global::_003CModule_003E.Dtapi_002EDtDvbC2PlpPars_002E_007Bdtor_007D), &dtDvbC2PlpPars);
			throw;
		}
		global::_003CModule_003E.std_002Evector_003CDtapi_003A_003ADtDvbC2XFecFrameHeader_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2XFecFrameHeader_003E_0020_003E_002E_Tidy((vector_003CDtapi_003A_003ADtDvbC2XFecFrameHeader_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2XFecFrameHeader_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtDvbC2PlpPars, 64)));
	}

	internal unsafe void ConvertToUnmgd(Dtapi.DtDvbC2PlpPars* uPlpPars)
	{
		((sbyte*)uPlpPars)[4] = (m_Hem ? ((sbyte)1) : ((sbyte)0));
		((sbyte*)uPlpPars)[5] = (m_Npd ? ((sbyte)1) : ((sbyte)0));
		((int*)uPlpPars)[2] = m_Issy;
		((int*)uPlpPars)[3] = m_IssyBufs;
		((int*)uPlpPars)[4] = m_IssyOutputDelay;
		((int*)uPlpPars)[5] = m_TsRate;
		*(int*)uPlpPars = m_Ccm;
		((int*)uPlpPars)[7] = m_Id;
		((sbyte*)uPlpPars)[32] = (m_Bundled ? ((sbyte)1) : ((sbyte)0));
		((int*)uPlpPars)[9] = m_Type;
		((int*)uPlpPars)[11] = m_GroupId;
		((int*)uPlpPars)[12] = m_FecType;
		((int*)uPlpPars)[13] = m_CodeRate;
		((int*)uPlpPars)[14] = m_Modulation;
		((int*)uPlpPars)[15] = m_HdrCntr;
		Dtapi.DtDvbC2PlpPars* ptr = (Dtapi.DtDvbC2PlpPars*)((byte*)uPlpPars + 64);
		vector_003CDtapi_003A_003ADtDvbC2XFecFrameHeader_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2XFecFrameHeader_003E_0020_003E* ptr2 = (vector_003CDtapi_003A_003ADtDvbC2XFecFrameHeader_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2XFecFrameHeader_003E_0020_003E*)ptr;
		((int*)ptr2)[1] = *(int*)ptr2;
		int num = 0;
		if (0 < m_AcmHeaders.Count)
		{
			System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDvbC2XFecFrameHeader dtDvbC2XFecFrameHeader);
			do
			{
				m_AcmHeaders[num].ConvertToUnmgd(&dtDvbC2XFecFrameHeader);
				global::_003CModule_003E.std_002Evector_003CDtapi_003A_003ADtDvbC2XFecFrameHeader_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2XFecFrameHeader_003E_0020_003E_002Eemplace_back_003Cstruct_0020Dtapi_003A_003ADtDvbC2XFecFrameHeader_0020const_0020_0026_003E((vector_003CDtapi_003A_003ADtDvbC2XFecFrameHeader_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2XFecFrameHeader_003E_0020_003E*)ptr, &dtDvbC2XFecFrameHeader);
				num++;
			}
			while (num < m_AcmHeaders.Count);
		}
		((sbyte*)uPlpPars)[76] = (m_PsiSiReproc ? ((sbyte)1) : ((sbyte)0));
		((int*)uPlpPars)[20] = m_TsId;
		((int*)uPlpPars)[21] = m_OnwId;
		((sbyte*)uPlpPars)[88] = (m_NoData ? ((sbyte)1) : ((sbyte)0));
	}

	internal unsafe void ConvertFromUnmgd(Dtapi.DtDvbC2PlpPars* uPlpPars)
	{
		m_Hem = ((bool*)uPlpPars)[4];
		m_Npd = ((bool*)uPlpPars)[5];
		m_Issy = ((int*)uPlpPars)[2];
		m_IssyBufs = ((int*)uPlpPars)[3];
		m_IssyOutputDelay = ((int*)uPlpPars)[4];
		m_TsRate = ((int*)uPlpPars)[5];
		m_Ccm = *(int*)uPlpPars;
		m_Id = ((int*)uPlpPars)[7];
		m_Bundled = ((bool*)uPlpPars)[32];
		m_Type = ((int*)uPlpPars)[9];
		m_GroupId = ((int*)uPlpPars)[11];
		m_FecType = ((int*)uPlpPars)[12];
		m_CodeRate = ((int*)uPlpPars)[13];
		m_Modulation = ((int*)uPlpPars)[14];
		m_HdrCntr = ((int*)uPlpPars)[15];
		m_AcmHeaders.Clear();
		uint num = 0u;
		vector_003CDtapi_003A_003ADtDvbC2XFecFrameHeader_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2XFecFrameHeader_003E_0020_003E* ptr = (vector_003CDtapi_003A_003ADtDvbC2XFecFrameHeader_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2XFecFrameHeader_003E_0020_003E*)((byte*)uPlpPars + 64);
		if (0u < (uint)((((int*)ptr)[1] - *(int*)ptr) / 20))
		{
			ptr = (vector_003CDtapi_003A_003ADtDvbC2XFecFrameHeader_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2XFecFrameHeader_003E_0020_003E*)((byte*)uPlpPars + 64);
			vector_003CDtapi_003A_003ADtDvbC2XFecFrameHeader_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2XFecFrameHeader_003E_0020_003E* ptr2 = (vector_003CDtapi_003A_003ADtDvbC2XFecFrameHeader_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2XFecFrameHeader_003E_0020_003E*)((byte*)ptr + 4);
			int num2 = 0;
			do
			{
				DtDvbC2XFecFrameHeader dtDvbC2XFecFrameHeader = new DtDvbC2XFecFrameHeader();
				dtDvbC2XFecFrameHeader.ConvertFromUnmgd((Dtapi.DtDvbC2XFecFrameHeader*)(num2 + ((int*)uPlpPars)[16]));
				m_AcmHeaders.Add(dtDvbC2XFecFrameHeader);
				num++;
				num2 += 20;
			}
			while (num < (uint)((*(int*)ptr2 - *(int*)ptr) / 20));
		}
		m_PsiSiReproc = ((bool*)uPlpPars)[76];
		m_TsId = ((int*)uPlpPars)[20];
		m_OnwId = ((int*)uPlpPars)[21];
		m_NoData = ((bool*)uPlpPars)[88];
	}
}
