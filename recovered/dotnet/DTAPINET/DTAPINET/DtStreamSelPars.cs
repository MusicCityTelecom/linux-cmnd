using System;
using Dtapi;

namespace DTAPINET;

public struct DtStreamSelPars
{
	public int m_Id;

	public DtStreamType m_StreamType;

	public DtConstelPars m_Constel;

	public DtAtsc3StreamSelPars m_Atsc3;

	public DtDabStreamSelPars m_Dab;

	public DtDabEtiStreamSelPars m_DabEti;

	public DtDabFicStreamSelPars m_DabFic;

	public DtDvbC2StreamSelPars m_DvbC2;

	public DtDvbTStreamSelPars m_DvbT;

	public DtDvbT2StreamSelPars m_DvbT2;

	public DtIsdbtStreamSelPars m_Isdbt;

	public DtImpRespPars m_ImpResp;

	public DtMerPars m_Mer;

	public DtSpectrumPars m_Spectrum;

	public DtT2MiStreamSelPars m_T2Mi;

	public DtTransFuncPars m_TransFunc;

	public DtTxIdImpRespPars m_TxIdImpResp;

	internal unsafe void ConvertToUnmgd(Dtapi.DtStreamSelPars* umSelPars)
	{
		*(int*)umSelPars = m_Id;
		((int*)umSelPars)[1] = (int)m_StreamType;
		switch (m_StreamType)
		{
		case DtStreamType.STREAM_CONSTEL:
			m_Constel.ConvertToUnmgd((Dtapi.DtConstelPars*)((byte*)umSelPars + 8));
			break;
		case DtStreamType.STREAM_ATSC3:
			((int*)umSelPars)[2] = m_Atsc3.m_PlpId;
			break;
		case DtStreamType.STREAM_DAB:
			m_Dab.ConvertToUnmgd((Dtapi.DtDabStreamSelPars*)((byte*)umSelPars + 8));
			break;
		case DtStreamType.STREAM_DABFIC:
		{
			ref DtDabFicStreamSelPars dabFic = ref m_DabFic;
			((int*)umSelPars)[2] = dabFic.m_CifIndex;
			((int*)((byte*)umSelPars + 8))[1] = dabFic.m_FibIndex;
			break;
		}
		case DtStreamType.STREAM_DVBC2:
			m_DvbC2.ConvertToUnmgd((Dtapi.DtDvbC2StreamSelPars*)((byte*)umSelPars + 8));
			break;
		case DtStreamType.STREAM_DVBT2:
		{
			ref DtDvbT2StreamSelPars dvbT = ref m_DvbT2;
			((int*)umSelPars)[2] = dvbT.m_PlpId;
			((int*)((byte*)umSelPars + 8))[1] = dvbT.m_CommonPlpId;
			break;
		}
		case DtStreamType.STREAM_IMPRESP:
		{
			ref DtImpRespPars impResp = ref m_ImpResp;
			((int*)umSelPars)[2] = impResp.m_Period;
			((int*)((byte*)umSelPars + 8))[1] = impResp.m_Channel;
			break;
		}
		case DtStreamType.STREAM_MER:
			((int*)umSelPars)[2] = m_Mer.m_Period;
			break;
		case DtStreamType.STREAM_SPECTRUM:
			m_Spectrum.ConvertToUnmgd((Dtapi.DtSpectrumPars*)((byte*)umSelPars + 8));
			break;
		case DtStreamType.STREAM_T2MI:
		{
			ref DtT2MiStreamSelPars t2Mi = ref m_T2Mi;
			((int*)umSelPars)[2] = t2Mi.m_T2MiOutPid;
			((int*)((byte*)umSelPars + 8))[1] = t2Mi.m_T2MiTsRate;
			break;
		}
		case DtStreamType.STREAM_TF_ABS:
		case DtStreamType.STREAM_TF_PHASE:
		case DtStreamType.STREAM_TF_GROUPDELAY:
		{
			ref DtTransFuncPars transFunc = ref m_TransFunc;
			((int*)umSelPars)[2] = transFunc.m_Period;
			((int*)((byte*)umSelPars + 8))[1] = transFunc.m_Channel;
			break;
		}
		case DtStreamType.STREAM_TXID_IMPRESP:
			m_TxIdImpResp.ConvertToUnmgd((Dtapi.DtTxIdImpRespPars*)((byte*)umSelPars + 8));
			break;
		default:
			throw new Exception("Invalid stream selection type");
		case DtStreamType.STREAM_DABETI:
		case DtStreamType.STREAM_DVBT:
		case DtStreamType.STREAM_ISDBT:
			break;
		}
	}

	internal unsafe void ConvertFromUnmgd(Dtapi.DtStreamSelPars* umSelPars)
	{
		m_Id = *(int*)umSelPars;
		m_StreamType = ((DtStreamType*)umSelPars)[1];
		switch (((int*)umSelPars)[1])
		{
		case 3:
			m_Constel.ConvertFromUnmgd((Dtapi.DtConstelPars*)((byte*)umSelPars + 8));
			break;
		case 1:
			m_Atsc3.m_PlpId = ((int*)umSelPars)[2];
			break;
		case 4:
			m_Dab.ConvertFromUnmgd((Dtapi.DtDabStreamSelPars*)((byte*)umSelPars + 8));
			break;
		case 6:
		{
			ref DtDabFicStreamSelPars dabFic = ref m_DabFic;
			dabFic.m_CifIndex = ((int*)umSelPars)[2];
			dabFic.m_FibIndex = ((int*)((byte*)umSelPars + 8))[1];
			break;
		}
		case 7:
			m_DvbC2.ConvertFromUnmgd((Dtapi.DtDvbC2StreamSelPars*)((byte*)umSelPars + 8));
			break;
		case 13:
		{
			ref DtDvbT2StreamSelPars dvbT = ref m_DvbT2;
			dvbT.m_PlpId = ((int*)umSelPars)[2];
			dvbT.m_CommonPlpId = ((int*)((byte*)umSelPars + 8))[1];
			break;
		}
		case 16:
		{
			ref DtImpRespPars impResp = ref m_ImpResp;
			impResp.m_Period = ((int*)umSelPars)[2];
			impResp.m_Channel = ((int*)((byte*)umSelPars + 8))[1];
			break;
		}
		case 18:
			m_Mer.m_Period = ((int*)umSelPars)[2];
			break;
		case 20:
			m_Spectrum.ConvertFromUnmgd((Dtapi.DtSpectrumPars*)((byte*)umSelPars + 8));
			break;
		case 21:
		{
			ref DtT2MiStreamSelPars t2Mi = ref m_T2Mi;
			((int*)umSelPars)[2] = t2Mi.m_T2MiOutPid;
			((int*)((byte*)umSelPars + 8))[1] = t2Mi.m_T2MiTsRate;
			break;
		}
		case 22:
		case 23:
		case 24:
		{
			ref DtTransFuncPars transFunc = ref m_TransFunc;
			transFunc.m_Period = ((int*)umSelPars)[2];
			transFunc.m_Channel = ((int*)((byte*)umSelPars + 8))[1];
			break;
		}
		case 25:
			m_TxIdImpResp.ConvertFromUnmgd((Dtapi.DtTxIdImpRespPars*)((byte*)umSelPars + 8));
			break;
		default:
			throw new Exception("Invalid stream selection type");
		case 5:
		case 12:
		case 17:
			break;
		}
	}
}
