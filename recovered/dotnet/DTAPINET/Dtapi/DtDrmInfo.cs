using System;
using System.Runtime.CompilerServices;
using System.Runtime.InteropServices;

namespace Dtapi;

[StructLayout(LayoutKind.Sequential, Size = 24)]
[NativeCppClass]
internal struct DtDrmInfo
{
	[CLSCompliant(false)]
	[NativeCppClass]
	public enum DrmRobustnessMode
	{

	}

	[NativeCppClass]
	[CLSCompliant(false)]
	public enum MscModulation
	{

	}
}
