using System;

namespace Philips.PSG.Gateway;

public static class Settings
{
	private const string PSG_APP_DATA_FOLDER = "\\Philips\\PSG";

	private const string CONFIG_FILE_NAME = "Configuration.xml";

	private const string CATALOG_FILE_NAME = "Catalog.xml";

	private const string DEFAULT_BUILD_ROOT_FOLDER = "%SystemDrive%\\Philips\\HotelTV";

	private const string DEFAULT_WORKING_ROOT_FOLDER = "%SystemDrive%\\Philips\\HotelTV\\PSG";

	private const string SCHEDULE_DATA_FOLDER_NAME = "OutputFiles";

	private const string OUTPUT_DATA_FOLDER_NAME = "OutputFiles";

	private const string MULTIPLEXED_TS_DATA_FILE_NAME = "MultiplexedTS.ts";

	private const string PREVIOUS_SCHEDULE_DATA_FOLDER_NAME = "PreviousFiles";

	private const string PREVIOUS_OUTPUT_DATA_FOLDER_NAME = "PreviousFiles";

	private const string SCHEDULE_DATA_FILE_NAME = "Schedule.xml";

	private const string SCHEDULE_RUN_FILE_NAME = "Run";

	private const string TS_PLAY_FILE_NAME = "TsPlay";

	private const string BUILD_LOG_FILE_NAME = "BuildLog.txt";

	private const int SCHEDULE_TIME_GRANULARITY_MINS = 5;

	public static string ConfigFile => "C:\\\\Philips\\\\SIServer\\\\PSG\\\\Configuration.xml";

	public static string CatalogFile => "C:\\\\Philips\\\\SIServer\\\\PSG\\\\Catalog.xml";

	public static string DefaultBuildRootFolder => Environment.ExpandEnvironmentVariables("%SystemDrive%\\Philips\\HotelTV");

	public static string DefaultWorkingRootFolder => Environment.ExpandEnvironmentVariables("%SystemDrive%\\Philips\\HotelTV\\PSG");

	public static string ScheduleDataFolderName => "OutputFiles";

	public static string OutputDataFolderName => "OutputFiles";

	public static string MultiplexedTsFileName => "MultiplexedTS.ts";

	public static string PreviousScheduleDataFolderName => "PreviousFiles";

	public static string PreviousOutputDataFolderName => "PreviousFiles";

	public static string ScheduleDataFileName => "Schedule.xml";

	public static string ScheduleRunFileName => "Run";

	public static string TsPlayFileName => "TsPlay";

	public static string BuildLogFileName => "BuildLog.txt";

	public static TimeSpan ScheduleTimeGranularity => new TimeSpan(0, 5, 0);
}
