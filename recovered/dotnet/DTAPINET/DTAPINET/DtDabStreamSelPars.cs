using Dtapi;

namespace DTAPINET;

public struct DtDabStreamSelPars
{
	public int m_BitrateKbps;

	public int m_ErrProtLevel;

	public int m_ErrProtMode;

	public int m_ErrProtOption;

	public int m_StartAddress;

	public DtDabExtractionMode m_ExtractionMode;

	internal unsafe void ConvertToUnmgd(Dtapi.DtDabStreamSelPars* uStremSel)
	{
		*(int*)uStremSel = m_BitrateKbps;
		((int*)uStremSel)[1] = m_ErrProtLevel;
		((int*)uStremSel)[2] = m_ErrProtMode;
		((int*)uStremSel)[3] = m_ErrProtOption;
		((int*)uStremSel)[4] = m_StartAddress;
		((int*)uStremSel)[5] = (int)m_ExtractionMode;
	}

	internal unsafe void ConvertFromUnmgd(Dtapi.DtDabStreamSelPars* uStremSel)
	{
		m_BitrateKbps = *(int*)uStremSel;
		m_ErrProtLevel = ((int*)uStremSel)[1];
		m_ErrProtMode = ((int*)uStremSel)[2];
		m_ErrProtOption = ((int*)uStremSel)[3];
		m_StartAddress = ((int*)uStremSel)[4];
		m_ExtractionMode = ((DtDabExtractionMode*)uStremSel)[5];
	}
}
