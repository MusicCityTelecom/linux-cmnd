using System.Collections.Generic;
using System.Runtime.CompilerServices;
using Dtapi;
using std;

namespace DTAPINET;

public class DtDvbT2TransmitterIdInfo
{
	public List<DtDvbT2TransmitterId> m_Transmitters;

	internal unsafe void ConvertToUnmgd(Dtapi.DtDvbT2TransmitterIdInfo* uDvbT2TxInfo)
	{
		((int*)uDvbT2TxInfo)[1] = *(int*)uDvbT2TxInfo;
		int num = 0;
		if (0 < m_Transmitters.Count)
		{
			System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDvbT2TransmitterId dtDvbT2TransmitterId2);
			do
			{
				DtDvbT2TransmitterId dtDvbT2TransmitterId = m_Transmitters[num];
				*(int*)(&dtDvbT2TransmitterId2) = dtDvbT2TransmitterId.m_TxId;
				System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtDvbT2TransmitterId, double>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtDvbT2TransmitterId2, 8)) = dtDvbT2TransmitterId.m_RelativePowerdB;
				global::_003CModule_003E.std_002Evector_003CDtapi_003A_003ADtDvbT2TransmitterId_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbT2TransmitterId_003E_0020_003E_002Eemplace_back_003Cstruct_0020Dtapi_003A_003ADtDvbT2TransmitterId_0020const_0020_0026_003E((vector_003CDtapi_003A_003ADtDvbT2TransmitterId_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbT2TransmitterId_003E_0020_003E*)uDvbT2TxInfo, &dtDvbT2TransmitterId2);
				num++;
			}
			while (num < m_Transmitters.Count);
		}
	}

	internal unsafe void ConvertFromUnmgd(Dtapi.DtDvbT2TransmitterIdInfo* uDvbT2TxInfo)
	{
		m_Transmitters.Clear();
		int num = 0;
		int num2 = *(int*)uDvbT2TxInfo;
		if (0 < ((((int*)uDvbT2TxInfo)[1] - num2) & -16))
		{
			int num3 = 0;
			do
			{
				DtDvbT2TransmitterId item = new DtDvbT2TransmitterId
				{
					m_TxId = *(int*)(num3 + num2),
					m_RelativePowerdB = *(double*)(num3 + num2 + 8)
				};
				m_Transmitters.Add(item);
				num++;
				num3 += 16;
				num2 = *(int*)uDvbT2TxInfo;
			}
			while (num < ((int*)uDvbT2TxInfo)[1] - num2 >> 4);
		}
	}

	public DtDvbT2TransmitterIdInfo()
	{
		m_Transmitters = new List<DtDvbT2TransmitterId>();
	}
}
