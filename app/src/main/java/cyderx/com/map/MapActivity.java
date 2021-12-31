package cyderx.com.map;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

import java.text.NumberFormat;

import cyderx.com.map.data.User;

public class MapActivity extends AppCompatActivity {

    private MapView mMapView;
    private BaiduMap mBaiduMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        MaterialToolbar topAppBar = findViewById(R.id.topAppBar);
        topAppBar.setNavigationOnClickListener(v -> {

        });

        // 菜单栏设置
        topAppBar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.info) {
                // 个人信息图标
                MapApplication app = (MapApplication) getApplication();
                User user = app.getUser();
                new MaterialAlertDialogBuilder(MapActivity.this)
                        .setTitle("个人信息").
                        setMessage(user.toString())
                        .setPositiveButton("知道啦", (dialog, which) -> {

                        })
                        .show();
            } else if (item.getItemId() == R.id.about) {
                // 关于
                new MaterialAlertDialogBuilder(MapActivity.this)
                        .setTitle("CyderMap").
                        setMessage("Made by Davy Chin\nHHU EIE 2018")
                        .setPositiveButton("感谢支持", (dialog, which) -> {

                        })
                        .show();
            } else if (item.getItemId() == R.id.locate) {
                // 经纬度定位
                LayoutInflater inflater = getLayoutInflater();
                View locateView = inflater.inflate(R.layout.locate, null);
                final EditText longitudeEditText = (EditText) locateView.findViewById(R.id.longitude_edit_text);
                final EditText latitudeEditText = (EditText) locateView.findViewById(R.id.latitude_edit_text);
                new MaterialAlertDialogBuilder(MapActivity.this)
                        .setTitle("经纬度定位")
                        .setView(locateView)
                        .setPositiveButton("定位", (dialog, which) -> {
                            // 检测空经纬度
                            if (TextUtils.isEmpty(longitudeEditText.getText()) || TextUtils.isEmpty(latitudeEditText.getText())) {
                                Snackbar.make(mMapView, "经纬度不能为空哦~", Snackbar.LENGTH_SHORT).show();
                                return;
                            }
                            // 检测是否包含其他字符
                            if (TextUtils.isDigitsOnly(longitudeEditText.getText()) && TextUtils.isDigitsOnly(latitudeEditText.getText())) {
                                double longitude = Double.parseDouble(longitudeEditText.getText().toString());
                                if (longitude < -180 || longitude > 180) {
                                    Snackbar.make(mMapView, "经度的范围在-180~180之间哦~", Snackbar.LENGTH_SHORT).show();
                                    return;
                                }
                                double latitude = Double.parseDouble(latitudeEditText.getText().toString());
                                if (latitude < -90 || latitude > 90) {
                                    Snackbar.make(mMapView, "纬度的范围在-90~90之间哦~", Snackbar.LENGTH_SHORT).show();
                                    return;
                                }
                                // 定义Maker坐标点
                                pointOverlay(longitude, latitude);
                                // 将地图移动过去
                                toNewAddress(longitude, latitude);
                                Snackbar.make(mMapView, "定位成功~", Snackbar.LENGTH_SHORT).show();
                            } else {
                                Snackbar.make(mMapView, "经纬度不要包含非法字符哦~", Snackbar.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("取消", (dialog, which) -> {

                        })
                        .show();
            } else if (item.getItemId() == R.id.city_locate) {
                // 城市定位
                LayoutInflater inflater = getLayoutInflater();
                View locateView = inflater.inflate(R.layout.city_locate, null);
                final EditText cityEditText = (EditText) locateView.findViewById(R.id.city_edit_text);
                new MaterialAlertDialogBuilder(MapActivity.this)
                        .setTitle("城市定位")
                        .setView(locateView)
                        .setPositiveButton("定位", (dialog, which) -> {
                            String city = cityEditText.getText().toString();
                            double longitude, latitude;
                            switch (city) {
                                case "南京":
                                case "nanjing":
                                    longitude = 118.8921;
                                    latitude = 31.32751;
                                    break;
                                case "北京":
                                case "beijing":
                                    longitude = 116.23128;
                                    latitude = 40.22077;
                                    break;
                                case "盐城":
                                case "yancheng":
                                    longitude = 120.50102;
                                    latitude = 33.20107;
                                    break;
                                default:
                                    Snackbar.make(mMapView, "数据还没更新哈，请耐心等待~", Snackbar.LENGTH_SHORT).show();
                                    return;
                            }
                            // 定义Maker坐标点
                            pointOverlay(longitude, latitude);
                            // 将地图移动过去
                            toNewAddress(longitude, latitude);
                            Snackbar.make(mMapView, "定位成功~", Snackbar.LENGTH_SHORT).show();
                        })
                        .setNegativeButton("取消", (dialog, which) -> {

                        })
                        .show();
            } else if (item.getItemId() == R.id.exit) {
                // 退出
                new MaterialAlertDialogBuilder(MapActivity.this)
                        .setTitle("退出")
                        .setMessage("确定要退出登陆嘛？")
                        .setPositiveButton("我要退出", (dialogInterface, i) -> {
                            Intent intent = new Intent(MapActivity.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);

                        })
                        .setNegativeButton("算了吧", (dialogInterface, i) -> {

                        })
                        .show();
            } else if (item.getItemId() == R.id.clear) {
                // 清屏
                mBaiduMap.clear();
                Snackbar.make(mMapView, "已清屏~", Snackbar.LENGTH_SHORT).show();
            } else if (item.getItemId() == R.id.cal_distance) {
                // 公里数计算
                LayoutInflater inflater = getLayoutInflater();
                View distanceView = inflater.inflate(R.layout.cal_distance, null);
                final EditText fromLongitudeEditText = (EditText) distanceView.findViewById(R.id.from_longitude_text);
                final EditText fromLatitudeEditText = (EditText) distanceView.findViewById(R.id.from_latitude_text);
                final EditText toLongitudeEditText = (EditText) distanceView.findViewById(R.id.to_longitude_text);
                final EditText toLatitudeEditText = (EditText) distanceView.findViewById(R.id.to_latitude_text);
                new MaterialAlertDialogBuilder(MapActivity.this)
                        .setTitle("公里数计算")
                        .setView(distanceView)
                        .setPositiveButton("计算", (dialog, which) -> {
                            // 检验空经纬度
                            if (TextUtils.isEmpty(fromLongitudeEditText.getText()) || TextUtils.isEmpty(fromLatitudeEditText.getText())
                                    || TextUtils.isEmpty(toLongitudeEditText.getText()) || TextUtils.isEmpty(toLatitudeEditText.getText())) {
                                Snackbar.make(mMapView, "经纬度不能为空哦~", Snackbar.LENGTH_SHORT).show();
                                return;
                            }
                            // 检验非法字符
                            if (TextUtils.isDigitsOnly(fromLongitudeEditText.getText()) && TextUtils.isDigitsOnly(fromLatitudeEditText.getText())
                                    && TextUtils.isDigitsOnly(toLongitudeEditText.getText()) && TextUtils.isDigitsOnly(toLatitudeEditText.getText())) {
                                double fromLongitude = Double.parseDouble(fromLongitudeEditText.getText().toString());
                                double fromLatitude = Double.parseDouble(fromLatitudeEditText.getText().toString());
                                double toLongitude = Double.parseDouble(toLongitudeEditText.getText().toString());
                                double toLatitude = Double.parseDouble(toLatitudeEditText.getText().toString());
                                if (fromLongitude < -180 || fromLongitude > 180
                                        || toLongitude < -180 || toLongitude > 180) {
                                    Snackbar.make(mMapView, "经度的范围在-180~180之间哦~", Snackbar.LENGTH_SHORT).show();
                                    return;
                                }
                                if (fromLatitude < -90 || fromLatitude > 90
                                        || toLatitude < -90 || toLatitude > 90) {
                                    Snackbar.make(mMapView, "纬度的范围在-90~90之间哦~", Snackbar.LENGTH_SHORT).show();
                                    return;
                                }
                                LatLng fromPoint = new LatLng(fromLatitude, fromLongitude);
                                LatLng toPoint = new LatLng(toLatitude, toLongitude);
                                double distance = DistanceUtil.getDistance(fromPoint, toPoint);
                                double kmDistance = distance / 1000.0;
                                // 不使用科学记数法
                                NumberFormat nf = NumberFormat.getInstance();
                                nf.setGroupingUsed(false);
                                // 两位小数
                                nf.setMaximumFractionDigits(2);
                                String text = "距离为：" + nf.format(kmDistance) + "km";
                                Snackbar.make(mMapView, text, Snackbar.LENGTH_SHORT).show();
                            } else {
                                Snackbar.make(mMapView, "经纬度不要包含非法字符哦~", Snackbar.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("取消", (dialog, which) -> {

                        })
                        .show();
            }
            return false;
        });

        mMapView = findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        //普通地图 ,mBaiduMap是地图控制器对象
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
    }

    @Override
    protected void onResume() {
        mMapView.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        super.onDestroy();
    }

    /**
     * 将地图的中心点移动到指定点
     *
     * @param longitude 经度
     * @param latitude  纬度
     */
    private void toNewAddress(double longitude, double latitude) {
        //设定中心点坐标
        LatLng cenpt = new LatLng(latitude, longitude);
        //定义地图状态
        MapStatus mapStatus = new MapStatus.Builder().target(cenpt).build();
        //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
        MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mapStatus);
        //改变地图状态
        mBaiduMap.setMapStatus(mapStatusUpdate);
    }

    /**
     * 绘制点标记，并将新的点标记添加到地图中
     *
     * @param longitude 经度
     * @param latitude  纬度
     */
    private void pointOverlay(double longitude, double latitude) {
        //定义Maker坐标点
        LatLng point = new LatLng(latitude, longitude);
        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.mark);
        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions().position(point).icon(bitmap);
        //在地图上添加Marker，并显示
        mBaiduMap.addOverlay(option);
    }
}