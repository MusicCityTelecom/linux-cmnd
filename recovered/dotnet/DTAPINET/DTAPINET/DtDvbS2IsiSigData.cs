using System.Collections.Generic;
using System.Runtime.CompilerServices;
using Dtapi;
using std;

namespace DTAPINET;

public class DtDvbS2IsiSigData
{
	public int m_Isi;

	public int m_FrameCount;

	public List<DtDvbS2PlsData> m_Pls;

	internal unsafe void ConvertToUnmgd(Dtapi.DtDvbS2IsiSigData* uS2SigData)
	{
		*(int*)uS2SigData = m_Isi;
		((int*)uS2SigData)[1] = m_FrameCount;
		Dtapi.DtDvbS2IsiSigData* ptr = (Dtapi.DtDvbS2IsiSigData*)((byte*)uS2SigData + 8);
		vector_003CDtapi_003A_003ADtDvbS2PlsData_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbS2PlsData_003E_0020_003E* ptr2 = (vector_003CDtapi_003A_003ADtDvbS2PlsData_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbS2PlsData_003E_0020_003E*)ptr;
		((int*)ptr2)[1] = *(int*)ptr2;
		int num = 0;
		if (0 < m_Pls.Count)
		{
			System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDvbS2PlsData dtDvbS2PlsData);
			do
			{
				global::_003CModule_003E.Dtapi_002EDtDvbS2PlsData_002E_007Bctor_007D(&dtDvbS2PlsData);
				m_Pls[num].ConvertToUnmgd(&dtDvbS2PlsData);
				global::_003CModule_003E.std_002Evector_003CDtapi_003A_003ADtDvbS2PlsData_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbS2PlsData_003E_0020_003E_002Eemplace_back_003Cstruct_0020Dtapi_003A_003ADtDvbS2PlsData_0020const_0020_0026_003E((vector_003CDtapi_003A_003ADtDvbS2PlsData_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbS2PlsData_003E_0020_003E*)ptr, &dtDvbS2PlsData);
				num++;
			}
			while (num < m_Pls.Count);
		}
	}

	internal unsafe void ConvertFromUnmgd(Dtapi.DtDvbS2IsiSigData* uS2SigData)
	{
		m_Isi = *(int*)uS2SigData;
		m_FrameCount = ((int*)uS2SigData)[1];
		m_Pls.Clear();
		uint num = 0u;
		vector_003CDtapi_003A_003ADtDvbS2PlsData_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbS2PlsData_003E_0020_003E* ptr = (vector_003CDtapi_003A_003ADtDvbS2PlsData_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbS2PlsData_003E_0020_003E*)((byte*)uS2SigData + 8);
		if (0u < (uint)((((int*)ptr)[1] - *(int*)ptr) / 20))
		{
			ptr = (vector_003CDtapi_003A_003ADtDvbS2PlsData_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbS2PlsData_003E_0020_003E*)((byte*)uS2SigData + 8);
			vector_003CDtapi_003A_003ADtDvbS2PlsData_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbS2PlsData_003E_0020_003E* ptr2 = (vector_003CDtapi_003A_003ADtDvbS2PlsData_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbS2PlsData_003E_0020_003E*)((byte*)ptr + 4);
			int num2 = 0;
			do
			{
				DtDvbS2PlsData dtDvbS2PlsData = new DtDvbS2PlsData();
				dtDvbS2PlsData.ConvertFromUnmgd((Dtapi.DtDvbS2PlsData*)(num2 + ((int*)uS2SigData)[2]));
				m_Pls.Add(dtDvbS2PlsData);
				num++;
				num2 += 20;
			}
			while (num < (uint)((*(int*)ptr2 - *(int*)ptr) / 20));
		}
	}

	public unsafe DtDvbS2IsiSigData()
	{
		m_Pls = new List<DtDvbS2PlsData>();
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDvbS2IsiSigData dtDvbS2IsiSigData);
		global::_003CModule_003E.Dtapi_002EDtDvbS2IsiSigData_002E_007Bctor_007D(&dtDvbS2IsiSigData);
		try
		{
			ConvertFromUnmgd(&dtDvbS2IsiSigData);
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtDvbS2IsiSigData*, void>)(&global::_003CModule_003E.Dtapi_002EDtDvbS2IsiSigData_002E_007Bdtor_007D), &dtDvbS2IsiSigData);
			throw;
		}
		global::_003CModule_003E.std_002Evector_003CDtapi_003A_003ADtDvbS2PlsData_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbS2PlsData_003E_0020_003E_002E_Tidy((vector_003CDtapi_003A_003ADtDvbS2PlsData_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbS2PlsData_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtDvbS2IsiSigData, 8)));
	}
}
