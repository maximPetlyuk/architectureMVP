package com.testing.skywell.architecture;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
import android.support.v4.app.Fragment;

public class AppBaseFragment extends Fragment implements FragmentInterface {

    protected FragmentInterface mFragmentContext;
    private ProgressDialog mProgressDialog;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mFragmentContext = ((FragmentInterface) context);
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement FragmentInterface");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mFragmentContext = null;
    }

    public FragmentInterface getActivityContext() {
        return mFragmentContext;
    }

    @Override
    public boolean onBack() {
        return true;
    }

    @Override
    public void doFragment(FragmentOperation operation) {
        if (null != mFragmentContext) {
            mFragmentContext.doFragment(operation);
        }
    }

    public void showProgressDialog(boolean show) {
        if (show) {
            mProgressDialog = ProgressDialog.show(getContext(), "", "Loading...", true);
        } else {
            if (mProgressDialog != null) {
                mProgressDialog.dismiss();
            }
        }
    }

    @Override
    public TabManager getTabManager() {
        return ((BaseActivity) getActivity()).getTabManager();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private byte[] saveBundle(final Bundle bundle) {
        Parcel parcel = Parcel.obtain();
        bundle.writeToParcel(parcel, 0);
        byte[] bytes = parcel.marshall();
        parcel.recycle();
        return bytes;
    }

    private Bundle loadBundle(byte[] array) {
        Parcel parcel = Parcel.obtain();
        parcel.unmarshall(array, 0, array.length);
        parcel.setDataPosition(0);
        return Bundle.CREATOR.createFromParcel(parcel);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("%s: Fragment '%s'", this.getClass().getSimpleName(), super.toString()));
        builder.append("\n");
        if (getArguments() != null) {
            for (String key : getArguments().keySet()) {
                Object object = getArguments().get(key).toString();
                builder.append(String.format("Bundle '%s' Value '%s'\n", key, object == null ? "null" : object.toString()));
            }
        } else {
            builder.append("Bundle is null");
        }
        builder.append("\n");
        return builder.toString();
    }
}


