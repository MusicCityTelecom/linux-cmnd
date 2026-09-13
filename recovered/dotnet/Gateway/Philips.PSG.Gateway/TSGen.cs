using System.Runtime.InteropServices;

namespace Philips.PSG.Gateway;

public static class TSGen
{
	public enum TS_Result
	{
		TS_RESULT_OK,
		TS_RESULT_ERROR_NO_FILES,
		TS_RESULT_ZERO_LENGTH_FILES,
		TS_RESULT_FAILED
	}

	public enum TS_DemodType
	{
		TS_DEMOD_TYPE_VSB,
		TS_DEMOD_TYPE_QAM64,
		TS_DEMOD_TYPE_QAM256
	}

	public enum TS_ProgressMessage
	{
		TS_PROGRESS_MESSAGE_PREPARING_CHANNEL_TABLE,
		TS_PROGRESS_MESSAGE_PREPARING_NVM,
		TS_PROGRESS_MESSAGE_PREPARING_THEME,
		TS_PROGRESS_MESSAGE_VALIDATING_INPUT,
		TS_PROGRESS_MESSAGE_GENERATING_TRANSPORT_STREAM,
		TS_PROGRESS_MESSAGE_MERGING_TRANSPORT_STREAM
	}

	public delegate void TS_ProgressCallback([MarshalAs(UnmanagedType.I4)] TS_ProgressMessage message);

	public struct TS_FileVersionDetails
	{
		[MarshalAs(UnmanagedType.ByValTStr, SizeConst = 10)]
		public string hardwareModel;

		[MarshalAs(UnmanagedType.ByValTStr, SizeConst = 10)]
		public string hardwareVersion;

		[MarshalAs(UnmanagedType.ByValTStr, SizeConst = 10)]
		public string softwareModel;

		[MarshalAs(UnmanagedType.ByValTStr, SizeConst = 10)]
		public string softwareVersion;
	}

	public struct TS_FileDetails
	{
		[MarshalAs(UnmanagedType.ByValTStr, SizeConst = 256)]
		public string filePath;

		[MarshalAs(UnmanagedType.I4)]
		public TS_FileType fileType;

		[MarshalAs(UnmanagedType.U4)]
		public uint room_number;

		[MarshalAs(UnmanagedType.ByValTStr, SizeConst = 256)]
		public string room_number_string;

		[MarshalAs(UnmanagedType.ByValTStr, SizeConst = 17)]
		public string ssb_identifier;

		[MarshalAs(UnmanagedType.ByValTStr, SizeConst = 85)]
		public string channel_table_identifier;

		[MarshalAs(UnmanagedType.Struct)]
		public TS_FileVersionDetails fileVersionDetails;
	}

	public struct TS_NetworkDetails
	{
		[MarshalAs(UnmanagedType.U2)]
		public ushort original_network_id;

		[MarshalAs(UnmanagedType.U4)]
		public uint centre_frequency;

		[MarshalAs(UnmanagedType.U2)]
		public ushort bandwidth;

		[MarshalAs(UnmanagedType.U2)]
		public ushort constellation;

		[MarshalAs(UnmanagedType.U2)]
		public ushort guard_interval;

		[MarshalAs(UnmanagedType.U2)]
		public ushort transmission_mode;

		[MarshalAs(UnmanagedType.U2)]
		public ushort code_rate;

		[MarshalAs(UnmanagedType.U2)]
		public ushort prognum;

		[MarshalAs(UnmanagedType.U2)]
		public ushort pid;

		[MarshalAs(UnmanagedType.U4)]
		public uint oui;

		[MarshalAs(UnmanagedType.U4)]
		public uint txnid;

		[MarshalAs(UnmanagedType.U4)]
		public uint dwnldid;

		[MarshalAs(UnmanagedType.U2)]
		public ushort network_id;

		[MarshalAs(UnmanagedType.U2)]
		public ushort transport_stream_id;

		[MarshalAs(UnmanagedType.U2)]
		public ushort pcrpid;

		[MarshalAs(UnmanagedType.U2)]
		public ushort hwmodel;

		[MarshalAs(UnmanagedType.U2)]
		public ushort hwver;

		[MarshalAs(UnmanagedType.U2)]
		public ushort swmodel;

		[MarshalAs(UnmanagedType.U2)]
		public ushort swver;
	}

	public enum TS_FileType
	{
		Clone,
		Upgrade,
		TransportStream
	}

	[DllImport("TSGen")]
	[return: MarshalAs(UnmanagedType.I4)]
	public static extern TS_Result TS_Generate([MarshalAs(UnmanagedType.FunctionPtr)] TS_ProgressCallback progressCB, [MarshalAs(UnmanagedType.LPStr)] string outputFile, [MarshalAs(UnmanagedType.LPArray)] TS_FileDetails[] fileDetails, [MarshalAs(UnmanagedType.I4)] int fileCount, [MarshalAs(UnmanagedType.Struct)] TS_NetworkDetails networkDetails);

	[DllImport("TSGen")]
	[return: MarshalAs(UnmanagedType.I4)]
	public static extern TS_Result TS_Merge([MarshalAs(UnmanagedType.FunctionPtr)] TS_ProgressCallback progressCB, [MarshalAs(UnmanagedType.LPStr)] string outputFile, [MarshalAs(UnmanagedType.LPArray)] TS_FileDetails[] fileDetails, [MarshalAs(UnmanagedType.I4)] int fileCount, [MarshalAs(UnmanagedType.Struct)] TS_NetworkDetails networkDetails);
}
