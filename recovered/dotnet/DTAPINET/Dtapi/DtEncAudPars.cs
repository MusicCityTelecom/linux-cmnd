using System;
using System.Runtime.CompilerServices;
using System.Runtime.InteropServices;

namespace Dtapi;

[StructLayout(LayoutKind.Sequential, Size = 72)]
[NativeCppClass]
internal struct DtEncAudPars
{
	[NativeCppClass]
	[CLSCompliant(false)]
	public enum AudServiceType
	{

	}
}
