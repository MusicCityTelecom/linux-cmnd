using Dtapi;

namespace DTAPINET;

public class DtIsdbtParamsData
{
	public int m_BType;

	public int m_Mode;

	public int m_Guard;

	public int m_PartialRx;

	public DtIsdbtLayerData[] m_LayerPars;

	public DtIsdbtParamsData()
	{
		m_LayerPars = new DtIsdbtLayerData[3];
	}

	internal unsafe void ConvertToUnmgd(Dtapi.DtIsdbtParamsData* umPars)
	{
		*(int*)umPars = m_BType;
		((int*)umPars)[1] = m_Mode;
		((int*)umPars)[2] = m_Guard;
		((int*)umPars)[3] = m_PartialRx;
		int num = 0;
		Dtapi.DtIsdbtParamsData* ptr = (Dtapi.DtIsdbtParamsData*)((byte*)umPars + 20);
		do
		{
			*((int*)ptr - 1) = m_LayerPars[num].m_NumSegments;
			*(int*)ptr = m_LayerPars[num].m_Modulation;
			((int*)ptr)[1] = m_LayerPars[num].m_CodeRate;
			((int*)ptr)[2] = m_LayerPars[num].m_TimeInterleave;
			num++;
			ptr = (Dtapi.DtIsdbtParamsData*)((byte*)ptr + 16);
		}
		while (num < 3);
	}

	internal unsafe void ConvertFromUnmgd(Dtapi.DtIsdbtParamsData* umPars)
	{
		m_BType = *(int*)umPars;
		m_Mode = ((int*)umPars)[1];
		m_Guard = ((int*)umPars)[2];
		m_PartialRx = ((int*)umPars)[3];
		int num = 0;
		Dtapi.DtIsdbtParamsData* ptr = (Dtapi.DtIsdbtParamsData*)((byte*)umPars + 20);
		do
		{
			m_LayerPars[num].m_NumSegments = *((int*)ptr - 1);
			m_LayerPars[num].m_Modulation = *(int*)ptr;
			m_LayerPars[num].m_CodeRate = ((int*)ptr)[1];
			m_LayerPars[num].m_TimeInterleave = ((int*)ptr)[2];
			num++;
			ptr = (Dtapi.DtIsdbtParamsData*)((byte*)ptr + 16);
		}
		while (num < 3);
	}
}
