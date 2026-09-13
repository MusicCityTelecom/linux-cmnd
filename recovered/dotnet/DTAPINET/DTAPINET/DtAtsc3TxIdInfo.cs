using System.Collections.Generic;
using System.Runtime.CompilerServices;
using Dtapi;
using std;

namespace DTAPINET;

public class DtAtsc3TxIdInfo
{
	public long m_ProgressCount;

	public List<DtAtsc3TxId> m_TxIds;

	public DtAtsc3TxIdInfo()
	{
		m_ProgressCount = 0L;
		m_TxIds = new List<DtAtsc3TxId>();
	}

	internal unsafe void ConvertToUnmgd(Dtapi.DtAtsc3TxIdInfo* uTxIdInfo)
	{
		*(long*)uTxIdInfo = m_ProgressCount;
		Dtapi.DtAtsc3TxIdInfo* ptr = (Dtapi.DtAtsc3TxIdInfo*)((byte*)uTxIdInfo + 8);
		vector_003CDtapi_003A_003ADtAtsc3TxId_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3TxId_003E_0020_003E* ptr2 = (vector_003CDtapi_003A_003ADtAtsc3TxId_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3TxId_003E_0020_003E*)ptr;
		((int*)ptr2)[1] = *(int*)ptr2;
		int num = 0;
		if (0 < m_TxIds.Count)
		{
			System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtAtsc3TxId dtAtsc3TxId);
			do
			{
				*(int*)(&dtAtsc3TxId) = m_TxIds[num].m_TxIdAddress;
				System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtAtsc3TxId, double>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtAtsc3TxId, 8)) = m_TxIds[num].m_LeveldB;
				global::_003CModule_003E.std_002Evector_003CDtapi_003A_003ADtAtsc3TxId_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3TxId_003E_0020_003E_002Eemplace_back_003Cstruct_0020Dtapi_003A_003ADtAtsc3TxId_0020const_0020_0026_003E((vector_003CDtapi_003A_003ADtAtsc3TxId_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3TxId_003E_0020_003E*)ptr, &dtAtsc3TxId);
				num++;
			}
			while (num < m_TxIds.Count);
		}
	}

	internal unsafe void ConvertFromUnmgd(Dtapi.DtAtsc3TxIdInfo* uTxIdInfo)
	{
		m_ProgressCount = *(long*)uTxIdInfo;
		m_TxIds.Clear();
		uint num = 0u;
		vector_003CDtapi_003A_003ADtAtsc3TxId_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3TxId_003E_0020_003E* ptr = (vector_003CDtapi_003A_003ADtAtsc3TxId_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3TxId_003E_0020_003E*)((byte*)uTxIdInfo + 8);
		if (0u < (uint)(((int*)ptr)[1] - *(int*)ptr >> 4))
		{
			ptr = (vector_003CDtapi_003A_003ADtAtsc3TxId_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3TxId_003E_0020_003E*)((byte*)uTxIdInfo + 8);
			vector_003CDtapi_003A_003ADtAtsc3TxId_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3TxId_003E_0020_003E* ptr2 = (vector_003CDtapi_003A_003ADtAtsc3TxId_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtAtsc3TxId_003E_0020_003E*)((byte*)ptr + 4);
			int num2 = 0;
			do
			{
				DtAtsc3TxId dtAtsc3TxId = new DtAtsc3TxId();
				dtAtsc3TxId.m_TxIdAddress = *(int*)(((int*)uTxIdInfo)[2] + num2);
				dtAtsc3TxId.m_LeveldB = *(double*)(num2 + ((int*)uTxIdInfo)[2] + 8);
				m_TxIds.Add(dtAtsc3TxId);
				num++;
				num2 += 16;
			}
			while (num < (uint)(*(int*)ptr2 - *(int*)ptr >> 4));
		}
	}
}
