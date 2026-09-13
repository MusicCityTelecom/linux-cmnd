using Dtapi;

namespace DTAPINET;

public struct DtDemodPlpBlocks
{
	public int m_NumBlocks;

	public int m_NumBlocksMin;

	public int m_NumBlocksMax;

	internal unsafe void ConvertToUnmgd(Dtapi.DtDemodPlpBlocks* umPars)
	{
		*(int*)umPars = m_NumBlocks;
		((int*)umPars)[1] = m_NumBlocksMin;
		((int*)umPars)[2] = m_NumBlocksMax;
	}

	internal unsafe void ConvertFromUnmgd(Dtapi.DtDemodPlpBlocks* umPars)
	{
		m_NumBlocks = *(int*)umPars;
		m_NumBlocksMin = ((int*)umPars)[1];
		m_NumBlocksMax = ((int*)umPars)[2];
	}
}
