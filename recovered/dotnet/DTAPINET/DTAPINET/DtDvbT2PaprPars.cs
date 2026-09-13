using Dtapi;

namespace DTAPINET;

public class DtDvbT2PaprPars
{
	public bool m_AceEnabled;

	public double m_AceVclip;

	public double m_AceGain;

	public double m_AceLimit;

	public int m_AceInterpFactor;

	public int m_AcePlpIndex;

	public bool m_TrEnabled;

	public bool m_TrP2Only;

	public double m_TrVclip;

	public int m_TrMaxIter;

	public int m_L1ExtLength;

	public bool m_L1AceEnabled;

	public double m_L1AceCMax;

	public bool m_L1Scrambling;

	public int m_NumBiasBalCells;

	public int m_BiasBalancing;

	public int m_TrAlgorithm;

	internal DtDvbT2PaprPars()
	{
	}

	internal unsafe void ConvertToUnmgd(Dtapi.DtDvbT2PaprPars* uPaprPars)
	{
		*(bool*)uPaprPars = m_AceEnabled;
		((double*)uPaprPars)[1] = m_AceVclip;
		((double*)uPaprPars)[2] = m_AceGain;
		((double*)uPaprPars)[3] = m_AceLimit;
		((int*)uPaprPars)[8] = m_AceInterpFactor;
		((int*)uPaprPars)[9] = m_AcePlpIndex;
		((sbyte*)uPaprPars)[40] = (m_TrEnabled ? ((sbyte)1) : ((sbyte)0));
		((sbyte*)uPaprPars)[41] = (m_TrP2Only ? ((sbyte)1) : ((sbyte)0));
		((double*)uPaprPars)[6] = m_TrVclip;
		((int*)uPaprPars)[14] = m_TrMaxIter;
		((int*)uPaprPars)[15] = m_L1ExtLength;
		((sbyte*)uPaprPars)[64] = (m_L1AceEnabled ? ((sbyte)1) : ((sbyte)0));
		((double*)uPaprPars)[9] = m_L1AceCMax;
		((sbyte*)uPaprPars)[80] = (m_L1Scrambling ? ((sbyte)1) : ((sbyte)0));
		((int*)uPaprPars)[21] = m_NumBiasBalCells;
		((int*)uPaprPars)[22] = m_BiasBalancing;
		((int*)uPaprPars)[23] = m_TrAlgorithm;
	}

	internal unsafe void ConvertFromUnmgd(Dtapi.DtDvbT2PaprPars* uPaprPars)
	{
		m_AceEnabled = *(bool*)uPaprPars;
		m_AceVclip = ((double*)uPaprPars)[1];
		m_AceGain = ((double*)uPaprPars)[2];
		m_AceLimit = ((double*)uPaprPars)[3];
		m_AceInterpFactor = ((int*)uPaprPars)[8];
		m_AcePlpIndex = ((int*)uPaprPars)[9];
		m_TrEnabled = ((bool*)uPaprPars)[40];
		m_TrP2Only = ((bool*)uPaprPars)[41];
		m_TrVclip = ((double*)uPaprPars)[6];
		m_TrMaxIter = ((int*)uPaprPars)[14];
		m_L1ExtLength = ((int*)uPaprPars)[15];
		m_L1AceEnabled = ((bool*)uPaprPars)[64];
		m_L1AceCMax = ((double*)uPaprPars)[9];
		m_L1Scrambling = ((bool*)uPaprPars)[80];
		m_NumBiasBalCells = ((int*)uPaprPars)[21];
		m_BiasBalancing = ((int*)uPaprPars)[22];
		m_TrAlgorithm = ((int*)uPaprPars)[23];
	}
}
