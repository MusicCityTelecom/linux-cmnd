using System.Runtime.CompilerServices;
using Dtapi;

namespace DTAPINET;

public class DtIoConfig
{
	public int m_Port;

	public int m_Group;

	public int m_Value;

	public int m_SubValue;

	public long[] m_ParXtra;

	internal unsafe void ConvertToUnmgd(Dtapi.DtIoConfig* uIoConfig)
	{
		*(int*)uIoConfig = m_Port;
		((int*)uIoConfig)[1] = m_Group;
		((int*)uIoConfig)[2] = m_Value;
		((int*)uIoConfig)[3] = m_SubValue;
		((long*)uIoConfig)[2] = m_ParXtra[0];
		((long*)uIoConfig)[3] = m_ParXtra[1];
	}

	internal unsafe void ConvertFromUnmgd(Dtapi.DtIoConfig* uIoConfig)
	{
		m_Port = *(int*)uIoConfig;
		m_Group = ((int*)uIoConfig)[1];
		m_Value = ((int*)uIoConfig)[2];
		m_SubValue = ((int*)uIoConfig)[3];
		ref long reference = ref m_ParXtra[0];
		reference = ((long*)uIoConfig)[2];
		ref long reference2 = ref m_ParXtra[1];
		reference2 = ((long*)uIoConfig)[3];
	}

	public unsafe DtIoConfig()
	{
		m_ParXtra = new long[2];
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtIoConfig dtIoConfig);
		global::_003CModule_003E.Dtapi_002EDtIoConfig_002E_007Bctor_007D(&dtIoConfig);
		ConvertFromUnmgd(&dtIoConfig);
	}
}
