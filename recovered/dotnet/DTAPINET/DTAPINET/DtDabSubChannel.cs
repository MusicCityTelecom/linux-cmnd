using Dtapi;

namespace DTAPINET;

public class DtDabSubChannel
{
	public int m_BitrateKbps;

	public int m_ErrProtLevel;

	public int m_ErrProtMode;

	public int m_ErrProtOption;

	public int m_FecScheme;

	public int m_StartAddress;

	public int m_SubChannelId;

	public int m_SubChannelSize;

	public int m_UepTableIndex;

	public int m_UepTableSwitch;

	internal unsafe void ConvertToUnmgd(Dtapi.DtDabSubChannel* uDabSubc)
	{
		*(int*)uDabSubc = m_BitrateKbps;
		((int*)uDabSubc)[1] = m_ErrProtLevel;
		((int*)uDabSubc)[2] = m_ErrProtMode;
		((int*)uDabSubc)[3] = m_ErrProtOption;
		((int*)uDabSubc)[4] = m_FecScheme;
		((int*)uDabSubc)[5] = m_StartAddress;
		((int*)uDabSubc)[6] = m_SubChannelId;
		((int*)uDabSubc)[7] = m_SubChannelSize;
		((int*)uDabSubc)[8] = m_UepTableIndex;
		((int*)uDabSubc)[9] = m_UepTableSwitch;
	}

	internal unsafe void ConvertFromUnmgd(Dtapi.DtDabSubChannel* uDabSubc)
	{
		m_BitrateKbps = *(int*)uDabSubc;
		m_ErrProtLevel = ((int*)uDabSubc)[1];
		m_ErrProtMode = ((int*)uDabSubc)[2];
		m_ErrProtOption = ((int*)uDabSubc)[3];
		m_FecScheme = ((int*)uDabSubc)[4];
		m_StartAddress = ((int*)uDabSubc)[5];
		m_SubChannelId = ((int*)uDabSubc)[6];
		m_SubChannelSize = ((int*)uDabSubc)[7];
		m_UepTableIndex = ((int*)uDabSubc)[8];
		m_UepTableSwitch = ((int*)uDabSubc)[9];
	}
}
