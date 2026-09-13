using System;
using Dtapi;

namespace DTAPINET;

public class DtDvbT2RbmEvent
{
	public interface RbmEventPars
	{
	}

	public struct Plot : RbmEventPars
	{
		public int m_TdiWriteIndex;

		public int m_TdiReadIndex;

		public int m_TdiReadAvailable;

		public int m_DjbSize;
	}

	public struct BufsTooSmall : RbmEventPars
	{
		public int m_Bufs;
	}

	public struct TtoInThePast : RbmEventPars
	{
		public int m_Tto;
	}

	public struct DjbOverflow : RbmEventPars
	{
		public int m_DjbSize;

		public int m_DjbMaxSize;
	}

	public struct Crc8ErrorHeader : RbmEventPars
	{
		public int m_Val;
	}

	public struct SyncDTooLarge : RbmEventPars
	{
		public int m_SyncD;

		public int m_Dfl;
	}

	public struct InvalidSyncD : RbmEventPars
	{
		public int m_Syncd;

		public int m_Left;
	}

	public struct TdiOverflow : RbmEventPars
	{
		public int m_TdiWriteIndex;

		public int m_TdiReadIndex;
	}

	public struct InvalidPlpStart : RbmEventPars
	{
		public int m_PlpId1;

		public int m_PlpId2;
	}

	public struct IscrError : RbmEventPars
	{
		public int m_Delta;
	}

	public struct BufsNotConstant : RbmEventPars
	{
		public int m_CurBufs;

		public int m_newBufs;
	}

	public struct PlpNumBlocksTooSmall : RbmEventPars
	{
		public int m_PlpNumBlocks;
	}

	public int m_DataPlpId;

	public int m_DataPlpIndex;

	public double m_Time;

	public int m_IsCommonPlp;

	public DtDvbT2RbmEventType m_EventType;

	public RbmEventPars m_EventPars;

	internal unsafe void ConvertFromUnmgd(Dtapi.DtDvbT2RbmEvent* uRbmEvent)
	{
		m_DataPlpId = *(int*)uRbmEvent;
		m_DataPlpIndex = ((int*)uRbmEvent)[1];
		m_Time = ((double*)uRbmEvent)[1];
		m_IsCommonPlp = ((int*)uRbmEvent)[4];
		m_EventType = ((DtDvbT2RbmEventType*)uRbmEvent)[5];
		switch (((int*)uRbmEvent)[5])
		{
		case 0:
		{
			ValueType valueType12 = default(Plot);
			m_EventPars = (RbmEventPars)valueType12;
			((Plot)valueType12).m_TdiWriteIndex = ((int*)uRbmEvent)[6];
			((Plot)valueType12).m_TdiReadIndex = ((int*)uRbmEvent)[7];
			((Plot)valueType12).m_TdiReadAvailable = ((int*)uRbmEvent)[8];
			((Plot)valueType12).m_DjbSize = ((int*)uRbmEvent)[9];
			break;
		}
		case 2:
		{
			ValueType valueType11 = default(BufsTooSmall);
			m_EventPars = (RbmEventPars)valueType11;
			((BufsTooSmall)valueType11).m_Bufs = ((int*)uRbmEvent)[6];
			break;
		}
		case 3:
		{
			ValueType valueType10 = default(TtoInThePast);
			m_EventPars = (RbmEventPars)valueType10;
			((TtoInThePast)valueType10).m_Tto = ((int*)uRbmEvent)[6];
			break;
		}
		case 4:
		{
			ValueType valueType9 = default(DjbOverflow);
			m_EventPars = (RbmEventPars)valueType9;
			((DjbOverflow)valueType9).m_DjbSize = ((int*)uRbmEvent)[6];
			((DjbOverflow)valueType9).m_DjbMaxSize = ((int*)uRbmEvent)[7];
			break;
		}
		case 5:
		{
			ValueType valueType8 = default(Crc8ErrorHeader);
			m_EventPars = (RbmEventPars)valueType8;
			((Crc8ErrorHeader)valueType8).m_Val = ((int*)uRbmEvent)[6];
			break;
		}
		case 6:
		{
			ValueType valueType7 = default(SyncDTooLarge);
			m_EventPars = (RbmEventPars)valueType7;
			((SyncDTooLarge)valueType7).m_SyncD = ((int*)uRbmEvent)[6];
			((SyncDTooLarge)valueType7).m_Dfl = ((int*)uRbmEvent)[7];
			break;
		}
		case 9:
		{
			ValueType valueType6 = default(InvalidSyncD);
			m_EventPars = (RbmEventPars)valueType6;
			((InvalidSyncD)valueType6).m_Syncd = ((int*)uRbmEvent)[6];
			((InvalidSyncD)valueType6).m_Left = ((int*)uRbmEvent)[7];
			break;
		}
		case 10:
		{
			ValueType valueType5 = default(TdiOverflow);
			m_EventPars = (RbmEventPars)valueType5;
			((TdiOverflow)valueType5).m_TdiWriteIndex = ((int*)uRbmEvent)[6];
			((TdiOverflow)valueType5).m_TdiReadIndex = ((int*)uRbmEvent)[7];
			break;
		}
		case 12:
		{
			ValueType valueType4 = default(InvalidPlpStart);
			m_EventPars = (RbmEventPars)valueType4;
			((InvalidPlpStart)valueType4).m_PlpId1 = ((int*)uRbmEvent)[6];
			((InvalidPlpStart)valueType4).m_PlpId2 = ((int*)uRbmEvent)[7];
			break;
		}
		case 15:
		{
			ValueType valueType3 = default(IscrError);
			m_EventPars = (RbmEventPars)valueType3;
			((IscrError)valueType3).m_Delta = ((int*)uRbmEvent)[6];
			break;
		}
		case 16:
		{
			ValueType valueType2 = default(BufsNotConstant);
			m_EventPars = (RbmEventPars)valueType2;
			((BufsNotConstant)valueType2).m_CurBufs = ((int*)uRbmEvent)[6];
			((BufsNotConstant)valueType2).m_newBufs = ((int*)uRbmEvent)[7];
			break;
		}
		case 19:
		{
			ValueType valueType = default(PlpNumBlocksTooSmall);
			m_EventPars = (RbmEventPars)valueType;
			((PlpNumBlocksTooSmall)valueType).m_PlpNumBlocks = ((int*)uRbmEvent)[6];
			break;
		}
		default:
			throw new Exception("Unknown RBM event type.");
		case 1:
		case 7:
		case 8:
		case 11:
		case 13:
		case 14:
		case 17:
		case 18:
			break;
		}
	}
}
