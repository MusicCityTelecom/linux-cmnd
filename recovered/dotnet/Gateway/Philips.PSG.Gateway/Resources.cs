using System.CodeDom.Compiler;
using System.ComponentModel;
using System.Diagnostics;
using System.Drawing;
using System.Globalization;
using System.Resources;
using System.Runtime.CompilerServices;

namespace Philips.PSG.Gateway;

[GeneratedCode("System.Resources.Tools.StronglyTypedResourceBuilder", "15.0.0.0")]
[DebuggerNonUserCode]
[CompilerGenerated]
internal class Resources
{
	private static ResourceManager resourceMan;

	private static CultureInfo resourceCulture;

	[EditorBrowsable(EditorBrowsableState.Advanced)]
	internal static ResourceManager ResourceManager
	{
		get
		{
			if (resourceMan == null)
			{
				resourceMan = new ResourceManager("Philips.PSG.Gateway.Resources", typeof(Resources).Assembly);
			}
			return resourceMan;
		}
	}

	[EditorBrowsable(EditorBrowsableState.Advanced)]
	internal static CultureInfo Culture
	{
		get
		{
			return resourceCulture;
		}
		set
		{
			resourceCulture = value;
		}
	}

	internal static string BTN_Browse => ResourceManager.GetString("BTN_Browse", resourceCulture);

	internal static string BTN_BuildSchedule => ResourceManager.GetString("BTN_BuildSchedule", resourceCulture);

	internal static string BTN_BuildTs => ResourceManager.GetString("BTN_BuildTs", resourceCulture);

	internal static string BTN_Cancel => ResourceManager.GetString("BTN_Cancel", resourceCulture);

	internal static string BTN_CopyScheduleEvent => ResourceManager.GetString("BTN_CopyScheduleEvent", resourceCulture);

	internal static string BTN_DeleteScheduleEvent => ResourceManager.GetString("BTN_DeleteScheduleEvent", resourceCulture);

	internal static string BTN_EditScheduleEvent => ResourceManager.GetString("BTN_EditScheduleEvent", resourceCulture);

	internal static string BTN_MoveScheduleEventDown => ResourceManager.GetString("BTN_MoveScheduleEventDown", resourceCulture);

	internal static string BTN_MoveScheduleEventUp => ResourceManager.GetString("BTN_MoveScheduleEventUp", resourceCulture);

	internal static string BTN_OK => ResourceManager.GetString("BTN_OK", resourceCulture);

	internal static string BTN_PauseSchedule => ResourceManager.GetString("BTN_PauseSchedule", resourceCulture);

	internal static string BTN_PlayTs => ResourceManager.GetString("BTN_PlayTs", resourceCulture);

	internal static string BTN_RunSchedule => ResourceManager.GetString("BTN_RunSchedule", resourceCulture);

	internal static string BTN_StopTs => ResourceManager.GetString("BTN_StopTs", resourceCulture);

	internal static string BTN_ViewBuildLog => ResourceManager.GetString("BTN_ViewBuildLog", resourceCulture);

	internal static string DLG_AboutTitle => ResourceManager.GetString("DLG_AboutTitle", resourceCulture);

	internal static string DLG_AboutVersion => ResourceManager.GetString("DLG_AboutVersion", resourceCulture);

	internal static string DLG_BuildActionCopyingMessage => ResourceManager.GetString("DLG_BuildActionCopyingMessage", resourceCulture);

	internal static string DLG_BuildActionGeneratingTransportStreamMessage => ResourceManager.GetString("DLG_BuildActionGeneratingTransportStreamMessage", resourceCulture);

	internal static string DLG_BuildActionMergingTransportStreamMessage => ResourceManager.GetString("DLG_BuildActionMergingTransportStreamMessage", resourceCulture);

	internal static string DLG_BuildActionPreparingChannelTableMessage => ResourceManager.GetString("DLG_BuildActionPreparingChannelTableMessage", resourceCulture);

	internal static string DLG_BuildActionPreparingFoldersMessage => ResourceManager.GetString("DLG_BuildActionPreparingFoldersMessage", resourceCulture);

	internal static string DLG_BuildActionPreparingNvmMessage => ResourceManager.GetString("DLG_BuildActionPreparingNvmMessage", resourceCulture);

	internal static string DLG_BuildActionPreparingThemeMessage => ResourceManager.GetString("DLG_BuildActionPreparingThemeMessage", resourceCulture);

	internal static string DLG_BuildActionStarting => ResourceManager.GetString("DLG_BuildActionStarting", resourceCulture);

	internal static string DLG_BuildFailedMessage => ResourceManager.GetString("DLG_BuildFailedMessage", resourceCulture);

	internal static string DLG_BuildProgressTitle => ResourceManager.GetString("DLG_BuildProgressTitle", resourceCulture);

	internal static string DLG_BuildSuccessfulMessage => ResourceManager.GetString("DLG_BuildSuccessfulMessage", resourceCulture);

	internal static string DLG_BuildTargetTvModelMessage => ResourceManager.GetString("DLG_BuildTargetTvModelMessage", resourceCulture);

	internal static string DLG_CatalogCreateFailedMessage => ResourceManager.GetString("DLG_CatalogCreateFailedMessage", resourceCulture);

	internal static string DLG_CatalogEntryInvalidMultipleFiles => ResourceManager.GetString("DLG_CatalogEntryInvalidMultipleFiles", resourceCulture);

	internal static string DLG_CatalogEntryInvalidPartialFileSet => ResourceManager.GetString("DLG_CatalogEntryInvalidPartialFileSet", resourceCulture);

	internal static string DLG_ConfigCreateFailedMessage => ResourceManager.GetString("DLG_ConfigCreateFailedMessage", resourceCulture);

	internal static string DLG_ConfigSaveFailedMessage => ResourceManager.GetString("DLG_ConfigSaveFailedMessage", resourceCulture);

	internal static string DLG_ConfigurationBuildRootFolderLabel => ResourceManager.GetString("DLG_ConfigurationBuildRootFolderLabel", resourceCulture);

	internal static string DLG_ConfigurationConfirmOutputChannelMessage => ResourceManager.GetString("DLG_ConfigurationConfirmOutputChannelMessage", resourceCulture);

	internal static string DLG_ConfigurationConstellationLabel => ResourceManager.GetString("DLG_ConfigurationConstellationLabel", resourceCulture);

	internal static string DLG_ConfigurationFrequencyFormat => ResourceManager.GetString("DLG_ConfigurationFrequencyFormat", resourceCulture);

	internal static string DLG_ConfigurationFrequencyLabel => ResourceManager.GetString("DLG_ConfigurationFrequencyLabel", resourceCulture);

	internal static string DLG_ConfigurationFrequencyUnit => ResourceManager.GetString("DLG_ConfigurationFrequencyUnit", resourceCulture);

	internal static string DLG_ConfigurationNotDefaultOutputChannelLabel => ResourceManager.GetString("DLG_ConfigurationNotDefaultOutputChannelLabel", resourceCulture);

	internal static string DLG_ConfigurationNotDefaultOutputFrequencyLabel => ResourceManager.GetString("DLG_ConfigurationNotDefaultOutputFrequencyLabel", resourceCulture);

	internal static string DLG_ConfigurationOutputChannelLabel => ResourceManager.GetString("DLG_ConfigurationOutputChannelLabel", resourceCulture);

	internal static string DLG_ConfigurationSelectBuildRootFolderMessage => ResourceManager.GetString("DLG_ConfigurationSelectBuildRootFolderMessage", resourceCulture);

	internal static string DLG_ConfigurationSelectWorkingRootFolderMessage => ResourceManager.GetString("DLG_ConfigurationSelectWorkingRootFolderMessage", resourceCulture);

	internal static string DLG_ConfigurationTitle => ResourceManager.GetString("DLG_ConfigurationTitle", resourceCulture);

	internal static string DLG_ConfigurationWorkingRootFolderLabel => ResourceManager.GetString("DLG_ConfigurationWorkingRootFolderLabel", resourceCulture);

	internal static string DLG_DekTecConfigurationCreateFailedMessage => ResourceManager.GetString("DLG_DekTecConfigurationCreateFailedMessage", resourceCulture);

	internal static string DLG_DekTecDeviceAttachFailedMessage => ResourceManager.GetString("DLG_DekTecDeviceAttachFailedMessage", resourceCulture);

	internal static string DLG_DekTecDeviceNotAvailable => ResourceManager.GetString("DLG_DekTecDeviceNotAvailable", resourceCulture);

	internal static string DLG_DekTecDeviceScanFailedMessage => ResourceManager.GetString("DLG_DekTecDeviceScanFailedMessage", resourceCulture);

	internal static string DLG_EditEndTimeLabel => ResourceManager.GetString("DLG_EditEndTimeLabel", resourceCulture);

	internal static string DLG_EditEventTitle => ResourceManager.GetString("DLG_EditEventTitle", resourceCulture);

	internal static string DLG_EditInvalidTimesMessage => ResourceManager.GetString("DLG_EditInvalidTimesMessage", resourceCulture);

	internal static string DLG_EditPlaybackFileLabel => ResourceManager.GetString("DLG_EditPlaybackFileLabel", resourceCulture);

	internal static string DLG_EditStartTimeLabel => ResourceManager.GetString("DLG_EditStartTimeLabel", resourceCulture);

	internal static string DLG_EditTvModelLabel => ResourceManager.GetString("DLG_EditTvModelLabel", resourceCulture);

	internal static string DLG_InvalidScheduleFooterMessage => ResourceManager.GetString("DLG_InvalidScheduleFooterMessage", resourceCulture);

	internal static string DLG_InvalidScheduleHeaderMessage => ResourceManager.GetString("DLG_InvalidScheduleHeaderMessage", resourceCulture);

	internal static string DLG_MissingAssemblyOrLibraryMessage => ResourceManager.GetString("DLG_MissingAssemblyOrLibraryMessage", resourceCulture);

	internal static string DLG_PreBuildFailedMessage => ResourceManager.GetString("DLG_PreBuildFailedMessage", resourceCulture);

	internal static string DLG_RunningByOtherUserMessage => ResourceManager.GetString("DLG_RunningByOtherUserMessage", resourceCulture);

	internal static string DLG_ScheduleCreateFailedMessage => ResourceManager.GetString("DLG_ScheduleCreateFailedMessage", resourceCulture);

	internal static string DLG_ScheduleSaveFailedMessage => ResourceManager.GetString("DLG_ScheduleSaveFailedMessage", resourceCulture);

	internal static string GLOBAL_AppName => ResourceManager.GetString("GLOBAL_AppName", resourceCulture);

	internal static string GRID_EndTimeColumnHeader => ResourceManager.GetString("GRID_EndTimeColumnHeader", resourceCulture);

	internal static string GRID_PlaybackFileColumnHeader => ResourceManager.GetString("GRID_PlaybackFileColumnHeader", resourceCulture);

	internal static string GRID_StartTimeColumnHeader => ResourceManager.GetString("GRID_StartTimeColumnHeader", resourceCulture);

	internal static string GRID_TvModelColumnHeader => ResourceManager.GetString("GRID_TvModelColumnHeader", resourceCulture);

	internal static Icon Icon => (Icon)ResourceManager.GetObject("Icon", resourceCulture);

	internal static string LBL_DekTecDeviceAvailable => ResourceManager.GetString("LBL_DekTecDeviceAvailable", resourceCulture);

	internal static string LBL_DekTecDeviceConfiguration => ResourceManager.GetString("LBL_DekTecDeviceConfiguration", resourceCulture);

	internal static string LBL_DekTecDeviceNotAvailable => ResourceManager.GetString("LBL_DekTecDeviceNotAvailable", resourceCulture);

	internal static string LBL_SchedulePriorityAxis => ResourceManager.GetString("LBL_SchedulePriorityAxis", resourceCulture);

	internal static Bitmap Logo => (Bitmap)ResourceManager.GetObject("Logo", resourceCulture);

	internal static string MENU_File => ResourceManager.GetString("MENU_File", resourceCulture);

	internal static string MENU_FileConfiguration => ResourceManager.GetString("MENU_FileConfiguration", resourceCulture);

	internal static string MENU_FileExit => ResourceManager.GetString("MENU_FileExit", resourceCulture);

	internal static string MENU_Help => ResourceManager.GetString("MENU_Help", resourceCulture);

	internal static string MENU_HelpAbout => ResourceManager.GetString("MENU_HelpAbout", resourceCulture);

	internal static Bitmap Philips => (Bitmap)ResourceManager.GetObject("Philips", resourceCulture);

	internal Resources()
	{
	}
}
