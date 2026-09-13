using Dtapi;

namespace DTAPINET;

public class DtMeasurement
{
	public long m_TimeStamp;

	public DtStreamType m_MeasurementType;

	public int m_NumValues;

	public DtComplexFloat[] m_pMeasurement;

	public unsafe void ConvertFromUnmgd(Dtapi.DtMeasurement* pMeasData)
	{
		m_TimeStamp = ((long*)pMeasData)[1];
		m_MeasurementType = *(DtStreamType*)pMeasData;
		m_NumValues = ((int*)pMeasData)[4];
		m_pMeasurement = new DtComplexFloat[((int*)pMeasData)[4]];
		int num = 0;
		if (0 < m_NumValues)
		{
			do
			{
				m_pMeasurement[num].m_Re = *(float*)(((int*)pMeasData)[5] + num * 8);
				m_pMeasurement[num].m_Im = *(float*)(num * 8 + ((int*)pMeasData)[5] + 4);
				num++;
			}
			while (num < m_NumValues);
		}
	}
}
