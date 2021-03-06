package xyz.belvi.premissiondialog;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import xyz.belvi.permissiondialog.Permission.PermissionDetails;
import xyz.belvi.permissiondialog.Permission.SmoothPermission;
import xyz.belvi.permissiondialog.Rationale.Rationale;
import xyz.belvi.permissiondialog.Rationale.RationaleResponse;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.click).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showPermDialog();
            }
        });


    }


    private void showPermDialog() {
        final PermissionDetails smsPermissionDetails = new PermissionDetails().getPermissionDetails(this, Manifest.permission.READ_SMS, R.drawable.ic_sms_white_24dp);
        Rationale.withActivity(this)
                .requestCode(PERM)
                .addSmoothPermission(new SmoothPermission(smsPermissionDetails))
                .includeStyle(R.style.Beliv_RationaleStyle).build(true);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private final int PERM = 2017;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Rationale.isResultFromRationale(requestCode, PERM)) {
            RationaleResponse rationaleResponse = Rationale.getRationaleResponse(data);
            if (rationaleResponse.shouldRequestForPermissions()) {
                // a list of permission to be requested for
                ArrayList<SmoothPermission> smoothPermissions = rationaleResponse.getSmoothPermissions();

                // request for permissions
            } else if (rationaleResponse.userDecline()) {
                Toast.makeText(this, "user does not want to do this now", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "permission is accepted", Toast.LENGTH_LONG).show();
            }
        }

    }

}
