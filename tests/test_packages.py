import tempfile
import unittest
from pathlib import Path
import zipfile
import threading
from http.server import ThreadingHTTPServer
from urllib.request import Request, urlopen

from cmnd_linux.packages import RangeHandler, build_room_package


class RoomPackageTests(unittest.TestCase):
    def test_leading_zero_room_id_is_preserved(self):
        with tempfile.TemporaryDirectory() as temp:
            base = Path(temp); template = base / "TVSettings.xml"; template.write_text("<TVSettings/>")
            output = base / "RoomSpecificSettings.zip"
            build_room_package(output, "SERIAL0001", "00704", template)
            with zipfile.ZipFile(output) as zf:
                self.assertEqual(set(zf.namelist()), {"RoomSpecificSettings.xml", "TVSettings.xml"})
                self.assertIn('Value="00704"', zf.read("RoomSpecificSettings.xml").decode())

    def test_non_numeric_room_rejected(self):
        with tempfile.TemporaryDirectory() as temp:
            template = Path(temp) / "TVSettings.xml"; template.write_text("x")
            with self.assertRaises(ValueError):
                build_room_package(Path(temp) / "out.zip", "SERIAL0001", "7A", template)

    def test_byte_range_response(self):
        with tempfile.TemporaryDirectory() as temp:
            package = Path(temp) / "package.zip"; package.write_bytes(b"PK" + bytes(range(100)))
            handler = lambda *a, **kw: RangeHandler(*a, directory=temp, **kw)
            server = ThreadingHTTPServer(("127.0.0.1", 0), handler)
            thread = threading.Thread(target=server.serve_forever, daemon=True); thread.start()
            try:
                request = Request(f"http://127.0.0.1:{server.server_port}/package.zip", headers={"Range": "bytes=10-19"})
                with urlopen(request, timeout=2) as response:
                    self.assertEqual(response.status, 206)
                    self.assertEqual(response.headers["Content-Range"], "bytes 10-19/102")
                    self.assertEqual(len(response.read()), 10)
            finally:
                server.shutdown(); server.server_close(); thread.join(timeout=2)


if __name__ == "__main__":
    unittest.main()
