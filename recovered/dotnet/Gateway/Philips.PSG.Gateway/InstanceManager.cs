using System;
using System.Diagnostics;
using System.Reflection;
using System.Runtime.InteropServices;
using System.Threading;

namespace Philips.PSG.Gateway;

public class InstanceManager
{
	public enum RunningState
	{
		NotRunning,
		RunningByThisUser,
		RunningByOtherUser
	}

	private static class SingleInstanceImplementor
	{
		private const uint WM_SYSCOMMAND = 274u;

		private const uint SC_RESTORE = 61728u;

		private static Mutex appMutex;

		[DllImport("user32.dll")]
		private static extern int SetForegroundWindow(IntPtr hWnd);

		[DllImport("user32.dll")]
		private static extern int PostMessage(IntPtr hWnd, uint sadf, uint l, uint w);

		[DllImport("user32.dll")]
		private static extern IntPtr FindWindow(string s, string s2);

		[DllImport("user32.dll")]
		private static extern uint GetWindowThreadProcessId(IntPtr hWnd, out uint pid);

		public static RunningState GetRunningState()
		{
			bool createdNew = true;
			string name = "Global\\" + GetMyUniqueStringID();
			try
			{
				appMutex = new Mutex(initiallyOwned: true, name, out createdNew);
				if (createdNew)
				{
					return RunningState.NotRunning;
				}
				return RunningState.RunningByThisUser;
			}
			catch (UnauthorizedAccessException)
			{
				return RunningState.RunningByOtherUser;
			}
			catch (Exception)
			{
				return RunningState.NotRunning;
			}
		}

		private static string GetMyUniqueStringID()
		{
			return ((GuidAttribute)Attribute.GetCustomAttribute(Assembly.GetExecutingAssembly(), typeof(GuidAttribute))).Value;
		}

		public static void SwitchToExistingInstance()
		{
			IntPtr existingWindowHandle = GetExistingWindowHandle();
			if (existingWindowHandle != IntPtr.Zero)
			{
				PostMessage(existingWindowHandle, 274u, 61728u, 0u);
				SetForegroundWindow(existingWindowHandle);
			}
		}

		private static IntPtr GetExistingWindowHandle()
		{
			IntPtr intPtr = IntPtr.Zero;
			Process currentProcess = Process.GetCurrentProcess();
			Process[] processesByName = Process.GetProcessesByName(currentProcess.ProcessName);
			foreach (Process process in processesByName)
			{
				if (process.Equals(currentProcess) || !process.MainModule.FileName.Equals(currentProcess.MainModule.FileName))
				{
					continue;
				}
				intPtr = process.MainWindowHandle;
				if (intPtr == IntPtr.Zero)
				{
					IntPtr intPtr2 = FindWindow(null, Resources.GLOBAL_AppName);
					if (intPtr2 != IntPtr.Zero)
					{
						GetWindowThreadProcessId(intPtr2, out var pid);
						if (pid == process.Id)
						{
							intPtr = intPtr2;
						}
					}
				}
				if (intPtr != IntPtr.Zero)
				{
					break;
				}
			}
			return intPtr;
		}
	}

	public static RunningState GetRunningState()
	{
		RunningState result = RunningState.NotRunning;
		try
		{
			result = SingleInstanceImplementor.GetRunningState();
		}
		catch (Exception)
		{
		}
		return result;
	}

	public static void SwitchToExistingInstance()
	{
		try
		{
			SingleInstanceImplementor.SwitchToExistingInstance();
		}
		catch (Exception)
		{
		}
	}
}
