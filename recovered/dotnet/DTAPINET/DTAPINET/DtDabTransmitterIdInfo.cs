using System.Collections.Generic;
using System.Runtime.CompilerServices;
using Dtapi;
using std;

namespace DTAPINET;

public class DtDabTransmitterIdInfo
{
	public List<DtDabTransmitterId> m_Transmitters;

	internal unsafe void ConvertToUnmgd(Dtapi.DtDabTransmitterIdInfo* uDabTxInfo)
	{
		((int*)uDabTxInfo)[1] = *(int*)uDabTxInfo;
		int num = 0;
		if (0 < m_Transmitters.Count)
		{
			System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDabTransmitterId dtDabTransmitterId);
			do
			{
				m_Transmitters[num].ConvertToUnmgd(&dtDabTransmitterId);
				global::_003CModule_003E.std_002Evector_003CDtapi_003A_003ADtDabTransmitterId_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDabTransmitterId_003E_0020_003E_002Eemplace_back_003Cstruct_0020Dtapi_003A_003ADtDabTransmitterId_0020const_0020_0026_003E((vector_003CDtapi_003A_003ADtDabTransmitterId_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDabTransmitterId_003E_0020_003E*)uDabTxInfo, &dtDabTransmitterId);
				num++;
			}
			while (num < m_Transmitters.Count);
		}
	}

	internal unsafe void ConvertFromUnmgd(Dtapi.DtDabTransmitterIdInfo* uDabTxInfo)
	{
		m_Transmitters.Clear();
		int num = 0;
		int num2 = *(int*)uDabTxInfo;
		if (0 < ((((int*)uDabTxInfo)[1] - num2) & -16))
		{
			int num3 = 0;
			do
			{
				DtDabTransmitterId item = default(DtDabTransmitterId);
				item.ConvertFromUnmgd((Dtapi.DtDabTransmitterId*)(num3 + num2));
				m_Transmitters.Add(item);
				num++;
				num3 += 16;
				num2 = *(int*)uDabTxInfo;
			}
			while (num < ((int*)uDabTxInfo)[1] - num2 >> 4);
		}
	}

	public DtDabTransmitterIdInfo()
	{
		m_Transmitters = new List<DtDabTransmitterId>();
	}
}
