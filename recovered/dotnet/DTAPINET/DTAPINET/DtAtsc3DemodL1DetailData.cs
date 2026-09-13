using System.Collections.Generic;
using System.Runtime.CompilerServices;
using Dtapi;
using std;

namespace DTAPINET;

public class DtAtsc3DemodL1DetailData
{
	public int m_Version;

	public int m_NumRf;

	public int[] m_BondedBsId;

	public int m_BsId;

	public int m_TimeSec;

	public int m_TimeMillisec;

	public int m_TimeMicrosec;

	public int m_TimeNanosec;

	public List<DtAtsc3DemodL1SubframeData> m_Subframes;

	public DtAtsc3DemodL1DetailData()
	{
		m_BondedBsId = new int[7];
		m_Subframes = new List<DtAtsc3DemodL1SubframeData>();
	}

	internal unsafe void ConvertToUnmgd(Dtapi.DtAtsc3DemodL1DetailData* uL1Detail)
	{
		*(int*)uL1Detail = m_Version;
		((int*)uL1Detail)[1] = m_NumRf;
		((int*)uL1Detail)[10] = m_TimeSec;
		((int*)uL1Detail)[11] = m_TimeMillisec;
		((int*)uL1Detail)[12] = m_TimeMicrosec;
		((int*)uL1Detail)[13] = m_TimeNanosec;
		((int*)uL1Detail)[9] = m_BsId;
		int num = 0;
		int[] bondedBsId = m_BondedBsId;
		if (0 < (nint)bondedBsId.LongLength)
		{
			Dtapi.DtAtsc3DemodL1DetailData* ptr = (Dtapi.DtAtsc3DemodL1DetailData*)((byte*)uL1Detail + 8);
			do
			{
				*(int*)ptr = bondedBsId[num];
				num++;
				ptr = (Dtapi.DtAtsc3DemodL1DetailData*)((byte*)ptr + 4);
				bondedBsId = m_BondedBsId;
			}
			while (num < (nint)bondedBsId.LongLength);
		}
		Dtapi.DtAtsc3DemodL1DetailData* ptr2 = (Dtapi.DtAtsc3DemodL1DetailData*)((byte*)uL1Detail + 64);
		vector_003CDtapi_003A_003ADtAtsc3DemodL1SubframeData_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3DemodL1SubframeData_003E_0020_003E* ptr3 = (vector_003CDtapi_003A_003ADtAtsc3DemodL1SubframeData_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3DemodL1SubframeData_003E_0020_003E*)ptr2;
		Dtapi.DtAtsc3DemodL1SubframeData* last = (Dtapi.DtAtsc3DemodL1SubframeData*)(int)((uint*)ptr3)[1];
		global::_003CModule_003E.std_002E_Destroy_range_003Cclass_0020std_003A_003Aallocator_003Cstruct_0020Dtapi_003A_003ADtAtsc3DemodL1SubframeData_003E_0020_003E((Dtapi.DtAtsc3DemodL1SubframeData*)(int)(*(uint*)ptr3), last, (allocator_003CDtapi_003A_003ADtAtsc3DemodL1SubframeData_003E*)ptr3);
		((int*)ptr3)[1] = *(int*)ptr3;
		int num2 = 0;
		if (0 >= m_Subframes.Count)
		{
			return;
		}
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtAtsc3DemodL1SubframeData dtAtsc3DemodL1SubframeData);
		do
		{
			System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtAtsc3DemodL1SubframeData, int>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtAtsc3DemodL1SubframeData, 48)) = 0;
			System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtAtsc3DemodL1SubframeData, int>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtAtsc3DemodL1SubframeData, 52)) = 0;
			System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtAtsc3DemodL1SubframeData, int>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtAtsc3DemodL1SubframeData, 56)) = 0;
			try
			{
				m_Subframes[num2].ConvertToUnmgd(&dtAtsc3DemodL1SubframeData);
				global::_003CModule_003E.std_002Evector_003CDtapi_003A_003ADtAtsc3DemodL1SubframeData_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3DemodL1SubframeData_003E_0020_003E_002Eemplace_back_003Cstruct_0020Dtapi_003A_003ADtAtsc3DemodL1SubframeData_0020const_0020_0026_003E((vector_003CDtapi_003A_003ADtAtsc3DemodL1SubframeData_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3DemodL1SubframeData_003E_0020_003E*)ptr2, &dtAtsc3DemodL1SubframeData);
			}
			catch
			{
				//try-fault
				global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtAtsc3DemodL1SubframeData*, void>)(&global::_003CModule_003E.Dtapi_002EDtAtsc3DemodL1SubframeData_002E_007Bdtor_007D), &dtAtsc3DemodL1SubframeData);
				throw;
			}
			global::_003CModule_003E.std_002Evector_003CDtapi_003A_003ADtAtsc3DemodL1PlpData_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3DemodL1PlpData_003E_0020_003E_002E_Tidy((vector_003CDtapi_003A_003ADtAtsc3DemodL1PlpData_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3DemodL1PlpData_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtAtsc3DemodL1SubframeData, 48)));
			num2++;
		}
		while (num2 < m_Subframes.Count);
	}

	internal unsafe void ConvertFromUnmgd(Dtapi.DtAtsc3DemodL1DetailData* uL1Detail)
	{
		m_Version = *(int*)uL1Detail;
		m_NumRf = ((int*)uL1Detail)[1];
		m_TimeSec = ((int*)uL1Detail)[10];
		m_TimeMillisec = ((int*)uL1Detail)[11];
		m_TimeMicrosec = ((int*)uL1Detail)[12];
		m_TimeNanosec = ((int*)uL1Detail)[13];
		m_BsId = ((int*)uL1Detail)[9];
		int num = 0;
		if (0 < (nint)m_BondedBsId.LongLength)
		{
			Dtapi.DtAtsc3DemodL1DetailData* ptr = (Dtapi.DtAtsc3DemodL1DetailData*)((byte*)uL1Detail + 8);
			do
			{
				ref int reference = ref m_BondedBsId[num];
				reference = *(int*)ptr;
				num++;
				ptr = (Dtapi.DtAtsc3DemodL1DetailData*)((byte*)ptr + 4);
			}
			while (num < (nint)m_BondedBsId.LongLength);
		}
		m_Subframes.Clear();
		uint num2 = 0u;
		vector_003CDtapi_003A_003ADtAtsc3DemodL1SubframeData_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3DemodL1SubframeData_003E_0020_003E* ptr2 = (vector_003CDtapi_003A_003ADtAtsc3DemodL1SubframeData_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3DemodL1SubframeData_003E_0020_003E*)((byte*)uL1Detail + 64);
		if (0u < (uint)((((int*)ptr2)[1] - *(int*)ptr2) / 60))
		{
			ptr2 = (vector_003CDtapi_003A_003ADtAtsc3DemodL1SubframeData_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3DemodL1SubframeData_003E_0020_003E*)((byte*)uL1Detail + 64);
			vector_003CDtapi_003A_003ADtAtsc3DemodL1SubframeData_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3DemodL1SubframeData_003E_0020_003E* ptr3 = (vector_003CDtapi_003A_003ADtAtsc3DemodL1SubframeData_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3DemodL1SubframeData_003E_0020_003E*)((byte*)ptr2 + 4);
			int num3 = 0;
			do
			{
				DtAtsc3DemodL1SubframeData dtAtsc3DemodL1SubframeData = new DtAtsc3DemodL1SubframeData();
				dtAtsc3DemodL1SubframeData.ConvertFromUnmgd((Dtapi.DtAtsc3DemodL1SubframeData*)(((int*)uL1Detail)[16] + num3));
				m_Subframes.Add(dtAtsc3DemodL1SubframeData);
				num2++;
				num3 += 60;
			}
			while (num2 < (uint)((*(int*)ptr3 - *(int*)ptr2) / 60));
		}
	}
}
