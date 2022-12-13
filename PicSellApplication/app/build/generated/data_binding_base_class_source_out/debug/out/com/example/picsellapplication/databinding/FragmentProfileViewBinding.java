// Generated by view binder compiler. Do not edit!
package com.example.picsellapplication.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.picsellapplication.R;
import com.mikhaellopez.circularimageview.CircularImageView;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentProfileViewBinding implements ViewBinding {
  @NonNull
  private final FrameLayout rootView;

  @NonNull
  public final Button btnChangePasswordDetails;

  @NonNull
  public final Button btnChangeProfileDetails;

  @NonNull
  public final CircularImageView circularImageView;

  @NonNull
  public final ImageView imageView5;

  @NonNull
  public final FrameLayout profileviewContainer;

  @NonNull
  public final TextView textView2;

  @NonNull
  public final TextView textView3;

  @NonNull
  public final TextView textView4;

  @NonNull
  public final TextView textView5;

  @NonNull
  public final TextView tvLogout;

  @NonNull
  public final TextView tvPassword;

  @NonNull
  public final TextView tvStoreName;

  @NonNull
  public final TextView tvUsername;

  @NonNull
  public final View view;

  @NonNull
  public final View view2;

  @NonNull
  public final View view3;

  @NonNull
  public final View view4;

  @NonNull
  public final View view5;

  private FragmentProfileViewBinding(@NonNull FrameLayout rootView,
      @NonNull Button btnChangePasswordDetails, @NonNull Button btnChangeProfileDetails,
      @NonNull CircularImageView circularImageView, @NonNull ImageView imageView5,
      @NonNull FrameLayout profileviewContainer, @NonNull TextView textView2,
      @NonNull TextView textView3, @NonNull TextView textView4, @NonNull TextView textView5,
      @NonNull TextView tvLogout, @NonNull TextView tvPassword, @NonNull TextView tvStoreName,
      @NonNull TextView tvUsername, @NonNull View view, @NonNull View view2, @NonNull View view3,
      @NonNull View view4, @NonNull View view5) {
    this.rootView = rootView;
    this.btnChangePasswordDetails = btnChangePasswordDetails;
    this.btnChangeProfileDetails = btnChangeProfileDetails;
    this.circularImageView = circularImageView;
    this.imageView5 = imageView5;
    this.profileviewContainer = profileviewContainer;
    this.textView2 = textView2;
    this.textView3 = textView3;
    this.textView4 = textView4;
    this.textView5 = textView5;
    this.tvLogout = tvLogout;
    this.tvPassword = tvPassword;
    this.tvStoreName = tvStoreName;
    this.tvUsername = tvUsername;
    this.view = view;
    this.view2 = view2;
    this.view3 = view3;
    this.view4 = view4;
    this.view5 = view5;
  }

  @Override
  @NonNull
  public FrameLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentProfileViewBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentProfileViewBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_profile_view, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentProfileViewBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btnChangePasswordDetails;
      Button btnChangePasswordDetails = ViewBindings.findChildViewById(rootView, id);
      if (btnChangePasswordDetails == null) {
        break missingId;
      }

      id = R.id.btnChangeProfileDetails;
      Button btnChangeProfileDetails = ViewBindings.findChildViewById(rootView, id);
      if (btnChangeProfileDetails == null) {
        break missingId;
      }

      id = R.id.circularImageView;
      CircularImageView circularImageView = ViewBindings.findChildViewById(rootView, id);
      if (circularImageView == null) {
        break missingId;
      }

      id = R.id.imageView5;
      ImageView imageView5 = ViewBindings.findChildViewById(rootView, id);
      if (imageView5 == null) {
        break missingId;
      }

      FrameLayout profileviewContainer = (FrameLayout) rootView;

      id = R.id.textView2;
      TextView textView2 = ViewBindings.findChildViewById(rootView, id);
      if (textView2 == null) {
        break missingId;
      }

      id = R.id.textView3;
      TextView textView3 = ViewBindings.findChildViewById(rootView, id);
      if (textView3 == null) {
        break missingId;
      }

      id = R.id.textView4;
      TextView textView4 = ViewBindings.findChildViewById(rootView, id);
      if (textView4 == null) {
        break missingId;
      }

      id = R.id.textView5;
      TextView textView5 = ViewBindings.findChildViewById(rootView, id);
      if (textView5 == null) {
        break missingId;
      }

      id = R.id.tvLogout;
      TextView tvLogout = ViewBindings.findChildViewById(rootView, id);
      if (tvLogout == null) {
        break missingId;
      }

      id = R.id.tvPassword;
      TextView tvPassword = ViewBindings.findChildViewById(rootView, id);
      if (tvPassword == null) {
        break missingId;
      }

      id = R.id.tvStoreName;
      TextView tvStoreName = ViewBindings.findChildViewById(rootView, id);
      if (tvStoreName == null) {
        break missingId;
      }

      id = R.id.tvUsername;
      TextView tvUsername = ViewBindings.findChildViewById(rootView, id);
      if (tvUsername == null) {
        break missingId;
      }

      id = R.id.view;
      View view = ViewBindings.findChildViewById(rootView, id);
      if (view == null) {
        break missingId;
      }

      id = R.id.view2;
      View view2 = ViewBindings.findChildViewById(rootView, id);
      if (view2 == null) {
        break missingId;
      }

      id = R.id.view3;
      View view3 = ViewBindings.findChildViewById(rootView, id);
      if (view3 == null) {
        break missingId;
      }

      id = R.id.view4;
      View view4 = ViewBindings.findChildViewById(rootView, id);
      if (view4 == null) {
        break missingId;
      }

      id = R.id.view5;
      View view5 = ViewBindings.findChildViewById(rootView, id);
      if (view5 == null) {
        break missingId;
      }

      return new FragmentProfileViewBinding((FrameLayout) rootView, btnChangePasswordDetails,
          btnChangeProfileDetails, circularImageView, imageView5, profileviewContainer, textView2,
          textView3, textView4, textView5, tvLogout, tvPassword, tvStoreName, tvUsername, view,
          view2, view3, view4, view5);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
