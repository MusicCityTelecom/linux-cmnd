using System.Runtime.InteropServices;
using Dtapi;

namespace DTAPINET;

[StructLayout(LayoutKind.Sequential, Size = 1)]
public struct DtIsdbtStreamSelPars
{
	internal unsafe void ConvertToUnmgd(Dtapi.DtIsdbtStreamSelPars* umSelPars)
	{
	}

	internal unsafe void ConvertFromUnmgd(Dtapi.DtIsdbtStreamSelPars* umSelPars)
	{
	}
}
