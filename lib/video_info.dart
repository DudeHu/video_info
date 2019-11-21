import 'dart:async';

import 'package:flutter/services.dart';
import 'dart:convert';

class VideoInfo {
  static const MethodChannel _channel =
      const MethodChannel('moon/video_info');

  static Future<List> get videoList async {
    final String videosStr = await _channel.invokeMethod('getVideoList');
    print(videosStr);
    var list = json.decode(videosStr) as List;
    return list;
  }
}


