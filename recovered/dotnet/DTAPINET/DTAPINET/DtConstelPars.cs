using Dtapi;

namespace DTAPINET;

public struct DtConstelPars
{
	public int m_Period;

	public int m_ConstellationType;

	public int m_Index;

	public int m_MaxNumPoints;

	internal unsafe void ConvertToUnmgd(Dtapi.DtConstelPars* umSelPars)
	{
		*(int*)umSelPars = m_Period;
		((int*)umSelPars)[1] = m_ConstellationType;
		((int*)umSelPars)[2] = m_Index;
		((int*)umSelPars)[3] = m_MaxNumPoints;
	}

	internal unsafe void ConvertFromUnmgd(Dtapi.DtConstelPars* umSelPars)
	{
		m_Period = *(int*)umSelPars;
		m_ConstellationType = ((int*)umSelPars)[1];
		m_Index = ((int*)umSelPars)[2];
		m_MaxNumPoints = ((int*)umSelPars)[3];
	}
}
