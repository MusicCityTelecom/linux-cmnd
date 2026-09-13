using System;
using System.Runtime.CompilerServices;
using System.Runtime.InteropServices;
using _003CCppImplementationDetails_003E;

namespace DTAPINET;

[StructLayout(LayoutKind.Sequential, Size = 1)]
public struct DVBT2_TESTPOINTS
{
	public unsafe static int Get(int Idx)
	{
		if (Idx >= 29)
		{
			throw new Exception("Index out of range.");
		}
		return *(int*)((ref *(_003F*)(Idx * 4)) + (ref System.Runtime.CompilerServices.Unsafe.As<_0024ArrayType_0024_0024_0024BY0BN_0040_0024_0024CBH, _003F>(ref global::_003CModule_003E.Dtapi_002EDTAPI_DVBT2_TESTPOINTS)));
	}
}
