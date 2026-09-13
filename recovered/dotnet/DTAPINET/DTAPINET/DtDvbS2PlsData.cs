using System.Runtime.CompilerServices;
using Dtapi;

namespace DTAPINET;

public class DtDvbS2PlsData
{
	public int m_FrameCount;

	public int m_ModType;

	public int m_CodeRate;

	public int m_Pilots;

	public int m_FecFrame;

	internal unsafe void ConvertToUnmgd(Dtapi.DtDvbS2PlsData* uS2SigData)
	{
		*(int*)uS2SigData = m_FrameCount;
		((int*)uS2SigData)[1] = m_ModType;
		((int*)uS2SigData)[2] = m_CodeRate;
		((int*)uS2SigData)[3] = m_Pilots;
		((int*)uS2SigData)[4] = m_FecFrame;
	}

	internal unsafe void ConvertFromUnmgd(Dtapi.DtDvbS2PlsData* uS2SigData)
	{
		m_FrameCount = *(int*)uS2SigData;
		m_ModType = ((int*)uS2SigData)[1];
		m_CodeRate = ((int*)uS2SigData)[2];
		m_Pilots = ((int*)uS2SigData)[3];
		m_FecFrame = ((int*)uS2SigData)[4];
	}

	public unsafe DtDvbS2PlsData()
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDvbS2PlsData dtDvbS2PlsData);
		global::_003CModule_003E.Dtapi_002EDtDvbS2PlsData_002E_007Bctor_007D(&dtDvbS2PlsData);
		ConvertFromUnmgd(&dtDvbS2PlsData);
	}
}
