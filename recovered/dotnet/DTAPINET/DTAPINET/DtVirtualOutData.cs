using System;
using System.Runtime.InteropServices;
using Dtapi;

namespace DTAPINET;

public class DtVirtualOutData
{
	public interface VirtualOutData
	{
	}

	public struct IqSamplesInt16 : VirtualOutData
	{
		public byte[][] m_pBuffer;

		public int m_NumBuffers;

		public int m_NumBytes;
	}

	public struct IqSamplesFloat32 : VirtualOutData
	{
		public byte[][] m_pBuffer;

		public int m_NumBuffers;

		public int m_NumBytes;
	}

	public struct T2MiTs188 : VirtualOutData
	{
		public byte[] m_pBuffer;

		public int m_NumBytes;

		public long m_T2MiFrameNr;
	}

	public enum OutDataType
	{
		IQ_INT16,
		IQ_FLOAT32,
		T2MI_TS188
	}

	public OutDataType m_DataType;

	public VirtualOutData m_Data;

	internal unsafe void ConvertFromUnmgd(Dtapi.DtVirtualOutData* uVoData)
	{
		m_DataType = *(OutDataType*)uVoData;
		switch (*(int*)uVoData)
		{
		default:
			throw new Exception("Unsupported Virtual Output datatype.");
		case 2:
		{
			ValueType valueType2 = default(T2MiTs188);
			m_Data = (VirtualOutData)valueType2;
			((T2MiTs188)valueType2).m_NumBytes = ((int*)uVoData)[3];
			((T2MiTs188)valueType2).m_T2MiFrameNr = ((long*)uVoData)[2];
			((T2MiTs188)valueType2).m_pBuffer = new byte[((T2MiTs188)valueType2).m_NumBytes];
			IntPtr source2 = new IntPtr((void*)(int)((uint*)uVoData)[2]);
			Marshal.Copy(source2, ((T2MiTs188)valueType2).m_pBuffer, 0, ((T2MiTs188)valueType2).m_NumBytes);
			break;
		}
		case 1:
		{
			ValueType valueType3 = default(IqSamplesFloat32);
			m_Data = (VirtualOutData)valueType3;
			((IqSamplesFloat32)valueType3).m_NumBuffers = ((int*)uVoData)[3];
			((IqSamplesFloat32)valueType3).m_NumBytes = ((int*)uVoData)[4];
			((IqSamplesFloat32)valueType3).m_pBuffer = new byte[((IqSamplesFloat32)valueType3).m_NumBuffers][];
			int num2 = 0;
			if (0 < ((IqSamplesFloat32)valueType3).m_NumBuffers)
			{
				Dtapi.DtVirtualOutData* ptr2 = (Dtapi.DtVirtualOutData*)((byte*)uVoData + 8);
				do
				{
					((IqSamplesFloat32)valueType3).m_pBuffer[num2] = new byte[((IqSamplesFloat32)valueType3).m_NumBytes];
					IntPtr source3 = new IntPtr((void*)(int)(*(uint*)(num2 * 4 + *(int*)ptr2)));
					Marshal.Copy(source3, ((IqSamplesFloat32)valueType3).m_pBuffer[num2], 0, ((IqSamplesFloat32)valueType3).m_NumBytes);
					num2++;
				}
				while (num2 < ((IqSamplesFloat32)valueType3).m_NumBuffers);
			}
			break;
		}
		case 0:
		{
			ValueType valueType = default(IqSamplesInt16);
			m_Data = (VirtualOutData)valueType;
			((IqSamplesInt16)valueType).m_NumBuffers = ((int*)uVoData)[3];
			((IqSamplesInt16)valueType).m_NumBytes = ((int*)uVoData)[4];
			((IqSamplesInt16)valueType).m_pBuffer = new byte[((IqSamplesInt16)valueType).m_NumBuffers][];
			int num = 0;
			if (0 < ((IqSamplesInt16)valueType).m_NumBuffers)
			{
				Dtapi.DtVirtualOutData* ptr = (Dtapi.DtVirtualOutData*)((byte*)uVoData + 8);
				do
				{
					((IqSamplesInt16)valueType).m_pBuffer[num] = new byte[((IqSamplesInt16)valueType).m_NumBytes];
					IntPtr source = new IntPtr((void*)(int)(*(uint*)(num * 4 + *(int*)ptr)));
					Marshal.Copy(source, ((IqSamplesInt16)valueType).m_pBuffer[num], 0, ((IqSamplesInt16)valueType).m_NumBytes);
					num++;
				}
				while (num < ((IqSamplesInt16)valueType).m_NumBuffers);
			}
			break;
		}
		}
	}
}
