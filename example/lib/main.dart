import 'package:flutter/material.dart';
import 'dart:async';
import 'dart:io';
import 'package:permission_handler/permission_handler.dart';
import 'package:video_info/video_info.dart';

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

Future<bool> _checkPermission() async {
  final permissionStorageGroup =
  Platform.isIOS ? PermissionGroup.photos : PermissionGroup.storage;
  Map<PermissionGroup, PermissionStatus> res =
  await PermissionHandler().requestPermissions([
    permissionStorageGroup,
  ]);
  return res[permissionStorageGroup] == PermissionStatus.granted;
}

class _MyAppState extends State<MyApp> {
  List _videoList = [];
  @override
  void initState() {
    super.initState();
    initVideoListState();
  }

  Future<void> initVideoListState() async {
    List videoList;
     // If the widget was removed from the tree while the asynchronous platform
    // message was in flight, we want to discard the reply rather than calling
    // setState to update our non-existent appearance.
    if (!mounted) return;
    try {
      var granted = await _checkPermission();
      if (!granted) return;
      videoList = await VideoInfo.videoList;
    } on Exception {}
    setState(() {
      _videoList = videoList;
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: Text('example app'),
        ),
        body: ListView.builder(
        itemCount: _videoList?.length ?? 0,
        itemBuilder: (context, index) {
          final _item = _videoList[index];
          return Card(
            child: ListTile(
              leading: _item != null && _item["thumbPath"] != null ? Image(
              image: new FileImage(File(_item["thumbPath"])),
              fit: BoxFit.cover
              ) : Icon(Icons.video_label),
              title: Text(_item["title"]),
              subtitle: Text(''),
              isThreeLine: true,
              onTap: () {
                print(_item);
              }
           )
          );
        })
      ),
    );
  }
}


