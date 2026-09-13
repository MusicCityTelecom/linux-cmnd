using Dtapi;

namespace DTAPINET;

public class DtVirtualOutPars
{
	public bool m_Enabled;

	public DtVirtualOutData.OutDataType m_DataType;

	public double m_Gain;

	internal DtVirtualOutPars()
	{
		m_Enabled = false;
	}

	internal unsafe void ConvertToUnmgd(Dtapi.DtVirtualOutPars* uVoPars)
	{
		*(bool*)uVoPars = m_Enabled;
		((int*)uVoPars)[1] = (int)m_DataType;
		((double*)uVoPars)[1] = m_Gain;
	}

	internal unsafe void ConvertFromUnmgd(Dtapi.DtVirtualOutPars* uVoPars)
	{
		m_Enabled = *(bool*)uVoPars;
		m_DataType = ((DtVirtualOutData.OutDataType*)uVoPars)[1];
		m_Gain = ((double*)uVoPars)[1];
	}
}
