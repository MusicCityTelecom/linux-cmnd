using System;
using System.Runtime.InteropServices;
using System.Windows.Forms;

namespace Philips.PSG.Gateway;

internal static class Program
{
	[DllImport("kernel32.dll")]
	private static extern bool AllocConsole();

	[STAThread]
	private static void Main(string[] args)
	{
		Application.EnableVisualStyles();
		Application.SetCompatibleTextRenderingDefault(defaultValue: false);
		InstanceManager.GetRunningState();
		if (args.Length == 0)
		{
			Console.WriteLine("Please enter a valid command.");
			Console.WriteLine("Press any key to continue");
		}
		else if (args.Length == 1)
		{
			if (args[0].ToString().ToUpper().Trim() == "BUILD" || args[0].ToString().ToUpper().Trim() == "PLAY")
			{
				MainForm mainForm = new MainForm();
				if (args[0].ToString().ToUpper().Trim() == "BUILD")
				{
					mainForm.BuildButton_Click(null, null);
				}
				else
				{
					mainForm.PlayPauseButton_Click(null, null);
				}
			}
			else
			{
				Console.WriteLine("Please enter a valid command.");
				Console.WriteLine("Press any key to continue");
			}
		}
		else
		{
			Console.WriteLine("Please enter a valid command.");
			Console.WriteLine("Press any key to continue");
		}
	}
}
