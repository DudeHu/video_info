import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:video_info/video_info.dart';

void main() {
  const MethodChannel channel = MethodChannel('video_info');

  TestWidgetsFlutterBinding.ensureInitialized();

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getVideoList', () async {
    expect(await VideoInfo.videoList, []);
  });
}
