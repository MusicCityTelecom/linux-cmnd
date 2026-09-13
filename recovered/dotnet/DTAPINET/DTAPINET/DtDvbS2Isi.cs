using System.Collections.Generic;
using System.Runtime.CompilerServices;
using Dtapi;
using std;

namespace DTAPINET;

public class DtDvbS2Isi
{
	public List<int> m_Isi;

	internal unsafe void ConvertToUnmgd(Dtapi.DtDvbS2Isi* uS2Isi)
	{
		((int*)uS2Isi)[1] = *(int*)uS2Isi;
		int num = 0;
		if (0 < m_Isi.Count)
		{
			do
			{
				int num2 = m_Isi[num];
				global::_003CModule_003E.std_002Evector_003Cint_002Cstd_003A_003Aallocator_003Cint_003E_0020_003E_002Eemplace_back_003Cint_003E((vector_003Cint_002Cstd_003A_003Aallocator_003Cint_003E_0020_003E*)uS2Isi, &num2);
				num++;
			}
			while (num < m_Isi.Count);
		}
	}

	internal unsafe void ConvertFromUnmgd(Dtapi.DtDvbS2Isi* uS2Isi)
	{
		m_Isi.Clear();
		uint num = 0u;
		int num2 = *(int*)uS2Isi;
		if (0u < (uint)(((int*)uS2Isi)[1] - num2 >> 2))
		{
			do
			{
				m_Isi.Add(*(int*)(int)(num * 4 + (uint)num2));
				num++;
				num2 = *(int*)uS2Isi;
			}
			while (num < (uint)(((int*)uS2Isi)[1] - num2 >> 2));
		}
	}

	public unsafe DtDvbS2Isi()
	{
		m_Isi = new List<int>();
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDvbS2Isi dtDvbS2Isi);
		global::_003CModule_003E.Dtapi_002EDtDvbS2Isi_002E_007Bctor_007D(&dtDvbS2Isi);
		try
		{
			ConvertFromUnmgd(&dtDvbS2Isi);
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtDvbS2Isi*, void>)(&global::_003CModule_003E.Dtapi_002EDtDvbS2Isi_002E_007Bdtor_007D), &dtDvbS2Isi);
			throw;
		}
		global::_003CModule_003E.std_002Evector_003Cint_002Cstd_003A_003Aallocator_003Cint_003E_0020_003E_002E_Tidy((vector_003Cint_002Cstd_003A_003Aallocator_003Cint_003E_0020_003E*)(&dtDvbS2Isi));
	}
}
