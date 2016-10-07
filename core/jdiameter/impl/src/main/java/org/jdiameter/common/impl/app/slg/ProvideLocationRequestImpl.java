/*
 * TeleStax, Open Source Cloud Communications
 * Copyright 2011-2016, Telestax Inc and individual contributors
 * by the @authors tag.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.jdiameter.common.impl.app.slg;

import org.jdiameter.api.Avp;
import org.jdiameter.api.AvpDataException;
import org.jdiameter.api.Message;
import org.jdiameter.api.slg.events.ProvideLocationRequest;
import org.jdiameter.common.impl.app.AppRequestEventImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author fernando.mendioroz@telestax.com (Fernando Mendioroz)
 *
 */
public class ProvideLocationRequestImpl extends AppRequestEventImpl implements ProvideLocationRequest {

  private static final long serialVersionUID = 1L;

  protected static final Logger logger = LoggerFactory.getLogger(ProvideLocationRequestImpl.class);

  public ProvideLocationRequestImpl(Message message) {
    super(message);
    message.setRequest(true);
  }

  @Override
  public boolean isSLgLocationTypeAVPPresent(){
    return super.message.getAvps().getAvp(Avp.SLG_LOCATION_TYPE) != null;
  }

  @Override
  public int getSLgLocationType(){
    Avp slgLocationTypeAvp = super.message.getAvps().getAvp(Avp.SLG_LOCATION_TYPE);
    if (slgLocationTypeAvp != null) {
      try {
        return slgLocationTypeAvp.getInteger32();
      } catch (AvpDataException e) {
        logger.debug("Failure trying to obtain SLg-Location-Type AVP value (IMSI)", e);
      }
    }
    return -1;
  }

  @Override
  public boolean isUserNameAVPPresent() {
    return super.message.getAvps().getAvp(Avp.USER_NAME) != null; // IE: IMSI
  }

  @Override
  public String getUserName() {
    Avp userNameAvp = super.message.getAvps().getAvp(Avp.USER_NAME);
    if (userNameAvp != null) {
      try {
        return userNameAvp.getUTF8String(); // IE: IMSI mapped to User-Name
      } catch (AvpDataException e) {
        logger.debug("Failure trying to obtain User-Name AVP value (IMSI)", e);
      }
    }
    return null;
  }

  @Override
  public boolean isMSISDNAVPPresent() {
    return super.message.getAvps().getAvp(Avp.MSISDN) != null;
  }

  @Override
  public byte[] getMSISDN() {
    Avp msisdnAvp = super.message.getAvps().getAvp(Avp.MSISDN);
    if (msisdnAvp != null) {
      try {
        return msisdnAvp.getOctetString();
      } catch (AvpDataException e) {
        logger.debug("Failure trying to obtain MSISDN AVP value", e);
      }
    }
    return null;
  }

  @Override
  public boolean isIMEIAVPPresent() {
    return super.message.getAvps().getAvp(Avp.TGPP_IMEI) != null;
  }

  @Override
  public String getIMEI() {
    Avp imeiAvp = super.message.getAvps().getAvp(Avp.TGPP_IMEI);
    if (imeiAvp != null) {
      try {
        return imeiAvp.getUTF8String();
      } catch (AvpDataException e) {
        logger.debug("Failure trying to obtain IMEI AVP value", e);
      }
    }
    return null;
  }

  @Override
  public boolean isLCSEPSClientNameAVPPresent(){
    return super.message.getAvps().getAvp(Avp.LCS_EPS_CLIENT_NAME) != null;
  }

  @Override
  public boolean isLSCNameStringAVPPresent(){
    Avp lcsEPSClientNameAvp = super.message.getAvps().getAvp(Avp.LCS_EPS_CLIENT_NAME);
    if (lcsEPSClientNameAvp != null) {
      try {
        return lcsEPSClientNameAvp.getGrouped().getAvp(Avp.LCS_NAME_STRING) != null;
      } catch (AvpDataException e) {
        logger.debug("Failure trying to obtain LCS-Name-String AVP", e);
      }
    }
    return false;
  }

  @Override
  public String getLSCNameString(){
    Avp lcsEPSClientNameAvp = super.message.getAvps().getAvp(Avp.LCS_EPS_CLIENT_NAME);
    if (lcsEPSClientNameAvp != null) {
      try {
        Avp lcsNameStringAvp = lcsEPSClientNameAvp.getGrouped().getAvp(Avp.LCS_NAME_STRING);
        if (lcsNameStringAvp != null){
          return lcsNameStringAvp.getUTF8String();
        }
      } catch (AvpDataException e) {
        logger.debug("Failure trying to obtain LCS-Name-String AVP value", e);
      }
    }
    return null;
  }

  @Override
  public boolean isLCSFormatIndicatorAVPPresent(){
    Avp lcsEPSClientNameAvp = super.message.getAvps().getAvp(Avp.LCS_EPS_CLIENT_NAME);
    if (lcsEPSClientNameAvp != null) {
      try {
        return lcsEPSClientNameAvp.getGrouped().getAvp(Avp.LCS_FORMAT_INDICATOR) != null;
      } catch (AvpDataException e) {
        logger.debug("Failure trying to obtain LCS-Format-Indicator AVP", e);
      }
    }
    return false;
  }

  @Override
  public int getLCSFormatIndicator(){
    Avp lcsEPSClientNameAvp = super.message.getAvps().getAvp(Avp.LCS_EPS_CLIENT_NAME);
    if (lcsEPSClientNameAvp != null) {
      try {
        Avp lcsFormatIndicatorAvp = lcsEPSClientNameAvp.getGrouped().getAvp(Avp.LCS_FORMAT_INDICATOR);
        if (lcsFormatIndicatorAvp != null){
          return lcsFormatIndicatorAvp.getInteger32();
        }
      } catch (AvpDataException e) {
        logger.debug("Failure trying to obtain LCS-Format-Indicator AVP value", e);
      }
    }
    return -1;
  }

  @Override
  public boolean isLCSCLientTypeAVPPresent(){
    return super.message.getAvps().getAvp(Avp.LCS_CLIENT_TYPE) != null;
  }

  @Override
  public int getLCSClientType(){
    Avp lcsClientTypeAvp = super.message.getAvps().getAvp(Avp.LCS_CLIENT_TYPE);
    if (lcsClientTypeAvp != null) {
      try {
        return lcsClientTypeAvp.getInteger32();
      } catch (AvpDataException e) {
        logger.debug("Failure trying to obtain LCS-Client-Type AVP value", e);
      }
    }
    return -1;
  }

  @Override
  public boolean isLCSRequestorNamePresent(){
    return super.message.getAvps().getAvp(Avp.LCS_REQUESTOR_NAME) != null;
  }

  @Override
  public boolean isLCSRequestorIdStringAVPPresent(){
    Avp lcsRequestorNameAvp = super.message.getAvps().getAvp(Avp.LCS_REQUESTOR_NAME);
    if (lcsRequestorNameAvp != null) {
      try {
        return lcsRequestorNameAvp.getGrouped().getAvp(Avp.LCS_REQUESTOR_ID_STRING) != null;
      } catch (AvpDataException e) {
        logger.debug("Failure trying to obtain LCS-Requestor-Id-String AVP", e);
      }
    }
    return false;
  }

  @Override
  public String getLCSRequestorIdString(){
    Avp lcsRequestorNameAvp = super.message.getAvps().getAvp(Avp.LCS_REQUESTOR_NAME);
    if (lcsRequestorNameAvp != null) {
      try {
        Avp lcsRequestorIdStringAvp = lcsRequestorNameAvp.getGrouped().getAvp(Avp.LCS_REQUESTOR_ID_STRING);
        if (lcsRequestorIdStringAvp != null){
          return lcsRequestorIdStringAvp.getUTF8String();
        }
      } catch (AvpDataException e) {
        logger.debug("Failure trying to obtain LCS-Requestor-Id-String AVP value", e);
      }
    }
    return null;
  }

  @Override
  public boolean isReqLCSFormatIndicatorAVPPresent(){
    Avp lcsRequestorNameAvp = super.message.getAvps().getAvp(Avp.LCS_REQUESTOR_NAME);
    if (lcsRequestorNameAvp != null) {
      try {
        return lcsRequestorNameAvp.getGrouped().getAvp(Avp.LCS_FORMAT_INDICATOR) != null;
      } catch (AvpDataException e) {
        logger.debug("Failure trying to obtain LCS-Format-Indicator AVP", e);
      }
    }
    return false;
  }

  @Override
  public int getReqLCSFormatIndicator(){
    Avp lcsRequestorNameAvp = super.message.getAvps().getAvp(Avp.LCS_REQUESTOR_NAME);
    if (lcsRequestorNameAvp != null) {
      try {
        Avp lcsFormatIndicatorAvp = lcsRequestorNameAvp.getGrouped().getAvp(Avp.LCS_FORMAT_INDICATOR);
        if (lcsFormatIndicatorAvp != null){
          return lcsFormatIndicatorAvp.getInteger32();
        }
      } catch (AvpDataException e) {
        logger.debug("Failure trying to obtain LCS-Format-Indicator AVP value", e);
      }
    }
    return -1;
  }

  @Override
  public boolean isLCSPriorityPresent(){
    return super.message.getAvps().getAvp(Avp.LCS_PRIORITY) != null;
  }

  @Override
  public long getLCSPriority(){
    Avp lcsPriorityAvp = super.message.getAvps().getAvp(Avp.LCS_PRIORITY);
    if (lcsPriorityAvp != null) {
      try {
        return lcsPriorityAvp.getUnsigned32();
      } catch (AvpDataException e) {
        logger.debug("Failure trying to obtain LCS-Priority AVP value", e);
      }
    }
    return -1;
  }

  @Override
  public boolean isLCSQoSAVPPresent(){
    return super.message.getAvps().getAvp(Avp.LCS_QOS) != null;
  }

  @Override
  public boolean isLCSQoSClassAVPPresent(){
    Avp lcsQoSAvp = super.message.getAvps().getAvp(Avp.LCS_QOS);
    if (lcsQoSAvp != null) {
      try {
        return lcsQoSAvp.getGrouped().getAvp(Avp.LCS_QOS_CLASS) != null;
      } catch (AvpDataException e) {
        logger.debug("Failure trying to obtain LCS-QoS-Class AVP", e);
      }
    }
    return false;
  }

  @Override
  public int getLCSQoSClass(){
    Avp lcsQoSAvp = super.message.getAvps().getAvp(Avp.LCS_QOS);
    if (lcsQoSAvp != null) {
      try {
        Avp lcsQoSClassAvp = lcsQoSAvp.getGrouped().getAvp(Avp.LCS_QOS_CLASS);
        if (lcsQoSClassAvp != null){
          return lcsQoSClassAvp.getInteger32();
        }
      } catch (AvpDataException e) {
        logger.debug("Failure trying to obtain LCS-QoS-Class AVP value", e);
      }
    }
    return -1;
  }

  @Override
  public boolean isHorizontalAccuracyAVPPresent(){
    Avp lcsQoSAvp = super.message.getAvps().getAvp(Avp.LCS_QOS);
    if (lcsQoSAvp != null) {
      try {
        return lcsQoSAvp.getGrouped().getAvp(Avp.HORIZONTAL_ACCURACY) != null;
      } catch (AvpDataException e) {
        logger.debug("Failure trying to obtain Horizontal-Accuracy AVP", e);
      }
    }
    return false;
  }

  @Override
  public long getHorizontalAccuracy(){
    Avp lcsQoSAvp = super.message.getAvps().getAvp(Avp.LCS_QOS);
    if (lcsQoSAvp != null) {
      try {
        Avp lcsQoSHorizontalAccuracyAvp = lcsQoSAvp.getGrouped().getAvp(Avp.HORIZONTAL_ACCURACY);
        if (lcsQoSHorizontalAccuracyAvp != null){
          return lcsQoSHorizontalAccuracyAvp.getUnsigned32();
        }
      } catch (AvpDataException e) {
        logger.debug("Failure trying to obtain Horizontal-Accuracy AVP value", e);
      }
    }
    return -1;
  }

  @Override
  public boolean isVerticalAccuracyAVPPresent(){
    Avp lcsQoSAvp = super.message.getAvps().getAvp(Avp.LCS_QOS);
    if (lcsQoSAvp != null) {
      try {
        return lcsQoSAvp.getGrouped().getAvp(Avp.VERTICAL_ACCURACY) != null;
      } catch (AvpDataException e) {
        logger.debug("Failure trying to obtain Vertical-Accuracy AVP", e);
      }
    }
    return false;
  }

  @Override
  public long getVerticalAccuracy(){
    Avp lcsQoSAvp = super.message.getAvps().getAvp(Avp.LCS_QOS);
    if (lcsQoSAvp != null) {
      try {
        Avp lcsQoSVerticalAccuracyAvp = lcsQoSAvp.getGrouped().getAvp(Avp.HORIZONTAL_ACCURACY);
        if (lcsQoSVerticalAccuracyAvp != null){
          return lcsQoSVerticalAccuracyAvp.getUnsigned32();
        }
      } catch (AvpDataException e) {
        logger.debug("Failure trying to obtain Vertical-Accuracy AVP value", e);
      }
    }
    return -1;
  }

  @Override
  public boolean isVerticalRequestedAVPPresent(){
    Avp lcsQoSAvp = super.message.getAvps().getAvp(Avp.LCS_QOS);
    if (lcsQoSAvp != null) {
      try {
        return lcsQoSAvp.getGrouped().getAvp(Avp.VERTICAL_REQUESTED) != null;
      } catch (AvpDataException e) {
        logger.debug("Failure trying to obtain Vertical-Requested AVP", e);
      }
    }
    return false;
  }

  public int getVerticalRequested(){
    Avp lcsQoSAvp = super.message.getAvps().getAvp(Avp.LCS_QOS);
    if (lcsQoSAvp != null) {
      try {
        Avp lcsQoSVerticalRequestedAvp = lcsQoSAvp.getGrouped().getAvp(Avp.VERTICAL_REQUESTED);
        if (lcsQoSVerticalRequestedAvp != null){
          return lcsQoSVerticalRequestedAvp.getInteger32();
        }
      } catch (AvpDataException e) {
        logger.debug("Failure trying to obtain Vertical-Requested AVP value", e);
      }
    }
    return -1;
  }

  @Override
  public boolean isResponseTimeAVPPresent(){
    Avp lcsQoSAvp = super.message.getAvps().getAvp(Avp.LCS_QOS);
    if (lcsQoSAvp != null) {
      try {
        return lcsQoSAvp.getGrouped().getAvp(Avp.RESPONSE_TIME) != null;
      } catch (AvpDataException e) {
        logger.debug("Failure trying to obtain Response-Time AVP", e);
      }
    }
    return false;
  }

  public int getResponseTime(){
    Avp lcsQoSAvp = super.message.getAvps().getAvp(Avp.LCS_QOS);
    if (lcsQoSAvp != null) {
      try {
        Avp lcsQoSResponseTimeAvp = lcsQoSAvp.getGrouped().getAvp(Avp.RESPONSE_TIME);
        if (lcsQoSResponseTimeAvp != null){
          return lcsQoSResponseTimeAvp.getInteger32();
        }
      } catch (AvpDataException e) {
        logger.debug("Failure trying to obtain Response-Time AVP value", e);
      }
    }
    return -1;
  }

  @Override
  public boolean isVelocityRequestedAVPPresent(){
    return super.message.getAvps().getAvp(Avp.VELOCITY_REQUESTED) != null;
  }

  @Override
  public byte[] getVelocityRequested(){
    Avp lcsVelocityRequestedAvp = super.message.getAvps().getAvp(Avp.VELOCITY_REQUESTED);
    if (lcsVelocityRequestedAvp != null) {
      try {
        return lcsVelocityRequestedAvp.getOctetString();
      } catch (AvpDataException e) {
        logger.debug("Failure trying to obtain LCS Velocity Requested AVP value", e);
      }
    }
    return null;
  }

  @Override
  public boolean isSupportedGADShapesAVPPresent(){
    return super.message.getAvps().getAvp(Avp.SUPPORTED_GAD_SHAPES) != null;
  }

  @Override
  public long getSupportedGADSahpes(){
    Avp lcsSupportedGADShapesAvp = super.message.getAvps().getAvp(Avp.SUPPORTED_GAD_SHAPES);
    if (lcsSupportedGADShapesAvp != null) {
      try {
        return lcsSupportedGADShapesAvp.getUnsigned32();
      } catch (AvpDataException e) {
        logger.debug("Failure trying to obtain LCS Supported GAD Shapes AVP value", e);
      }
    }
    return -1;
  }

  @Override
  public boolean isLSCServiceTypeIdAVPPresent(){
    return super.message.getAvps().getAvp(Avp.LCS_SERVICE_TYPE_ID) != null;
  }

  @Override
  public long getLSCServiceTypeId(){
    Avp lcsServiceTypeIdAvp = super.message.getAvps().getAvp(Avp.LCS_SERVICE_TYPE_ID);
    if (lcsServiceTypeIdAvp != null) {
      try {
        return lcsServiceTypeIdAvp.getUnsigned32();
      } catch (AvpDataException e) {
        logger.debug("Failure trying to obtain LCS Service Type ID AVP value", e);
      }
    }
    return -1;
  }

  @Override
  public boolean isLCSCodewordAVPPresent(){
    return super.message.getAvps().getAvp(Avp.LCS_CODEWORD) != null;
  }

  @Override
  public String getLCSCodeword(){
    Avp lcsCodewordAvp = super.message.getAvps().getAvp(Avp.LCS_CODEWORD);
    if (lcsCodewordAvp != null) {
      try {
        return lcsCodewordAvp.getUTF8String();
      } catch (AvpDataException e) {
        logger.debug("Failure trying to obtain LCS Codeword AVP value", e);
      }
    }
    return null;
  }

  @Override
  public boolean isServiceSelectionAVPPresent(){
    return super.message.getAvps().getAvp(Avp.SERVICE_SELECTION) != null;
  }

  @Override
  public String getServiceSelection(){
    Avp lcsServiceSelectionAvp = super.message.getAvps().getAvp(Avp.SERVICE_SELECTION);
    if (lcsServiceSelectionAvp != null) {
      try {
        return lcsServiceSelectionAvp.getUTF8String();
      } catch (AvpDataException e) {
        logger.debug("Failure trying to obtain LCS Service Selection AVP value", e);
      }
    }
    return null;
  }

  @Override
  public boolean isLCSPrivacyCheckSessionAVPPresent(){
    return super.message.getAvps().getAvp(Avp.LCS_PRIVACY_CHECK_SESSION) != null;
  }

  @Override
  public int getLCSPrivacyCheckSession(){
    Avp lcsPrivacyCheckSessionAvp = super.message.getAvps().getAvp(Avp.LCS_PRIVACY_CHECK_SESSION);
    if (lcsPrivacyCheckSessionAvp != null) {
      try {
        return lcsPrivacyCheckSessionAvp.getInteger32();
      } catch (AvpDataException e) {
        logger.debug("Failure trying to obtain LCS Privacy Check Session AVP value", e);
      }
    }
    return -1;
  }

  @Override
  public boolean isLCSPrivacyCheckNonSessionAVPPresent(){
    return super.message.getAvps().getAvp(Avp.LCS_PRIVACY_CHECK_NON_SESSION) != null;
  }

  @Override
  public int getLCSPrivacyCheckNonSession(){
    Avp lcsPrivacyCheckNonSessionAvp = super.message.getAvps().getAvp(Avp.LCS_PRIVACY_CHECK_NON_SESSION);
    if (lcsPrivacyCheckNonSessionAvp != null) {
      try {
        return lcsPrivacyCheckNonSessionAvp.getInteger32();
      } catch (AvpDataException e) {
        logger.debug("Failure trying to obtain LCS Privacy Check Non Session AVP value", e);
      }
    }
    return -1;
  }

  @Override
  public boolean isDeferredLocationTypeAVPPresent(){
    return super.message.getAvps().getAvp(Avp.DEFERRED_LOCATION_TYPE) != null;
  }

  @Override
  public long getDeferredLocationType(){
    Avp lcsDeferredLocationTypeAvp = super.message.getAvps().getAvp(Avp.DEFERRED_LOCATION_TYPE);
    if (lcsDeferredLocationTypeAvp != null) {
      try {
        return lcsDeferredLocationTypeAvp.getUnsigned32();
      } catch (AvpDataException e) {
        logger.debug("Failure trying to obtain LCS Deferred Location Type AVP value", e);
      }
    }
    return -1;
  }

  @Override
  public boolean isLCSReferenceNumberAVPPresent(){
    return super.message.getAvps().getAvp(Avp.LCS_REFERENCE_NUMBER) != null;
  }

  @Override
  public byte[] getLCSReferenceNumber(){
    Avp lcsLCSReferenceNumberAvp = super.message.getAvps().getAvp(Avp.LCS_REFERENCE_NUMBER);
    if (lcsLCSReferenceNumberAvp != null) {
      try {
        return lcsLCSReferenceNumberAvp.getOctetString();
      } catch (AvpDataException e) {
        logger.debug("Failure trying to obtain LCS Reference Number AVP value", e);
      }
    }
    return null;
  }

  @Override
  public boolean isAreaEventInfoAVPPresent(){
    return super.message.getAvps().getAvp(Avp.AREA_EVENT_INFO) != null;
  }

  @Override
  public boolean isAreaTypeAVPPresent(){
    Avp lcsAreaEventInfoAvp = super.message.getAvps().getAvp(Avp.AREA_EVENT_INFO);
    if (lcsAreaEventInfoAvp != null) {
      try {
        return lcsAreaEventInfoAvp.getGrouped().getAvp(Avp.AREA_TYPE) != null;
      } catch (AvpDataException e) {
        logger.debug("Failure trying to obtain Area-Type AVP", e);
      }
    }
    return false;
  }

  @Override
  public long getAreaType(){
    Avp lcsAreaEventInfoAvp = super.message.getAvps().getAvp(Avp.AREA_EVENT_INFO);
    if (lcsAreaEventInfoAvp != null) {
      try {
        Avp lcsAreaTypeAvp = lcsAreaEventInfoAvp.getGrouped().getAvp(Avp.AREA_TYPE);
        if (lcsAreaTypeAvp != null){
          return lcsAreaTypeAvp.getUnsigned32();
        }
      } catch (AvpDataException e) {
        logger.debug("Failure trying to obtain Area-Type AVP value", e);
      }
    }
    return -1;
  }

  @Override
  public boolean isAreaIdentificationAVPPresent(){
    Avp lcsAreaEventInfoAvp = super.message.getAvps().getAvp(Avp.AREA_EVENT_INFO);
    if (lcsAreaEventInfoAvp != null) {
      try {
        return lcsAreaEventInfoAvp.getGrouped().getAvp(Avp.AREA_IDENTIFICATION) != null;
      } catch (AvpDataException e) {
        logger.debug("Failure trying to obtain Area-Identification AVP", e);
      }
    }
    return false;
  }

  @Override
  public byte[] getAreaIdentification(){
    Avp lcsAreaEventInfoAvp = super.message.getAvps().getAvp(Avp.AREA_EVENT_INFO);
    if (lcsAreaEventInfoAvp != null) {
      try {
        Avp lcsAreaIdAvp = lcsAreaEventInfoAvp.getGrouped().getAvp(Avp.AREA_IDENTIFICATION);
        if (lcsAreaIdAvp != null){
          return lcsAreaIdAvp.getOctetString();
        }
      } catch (AvpDataException e) {
        logger.debug("Failure trying to obtain LCS Area Identification AVP value", e);
      }
    }
    return null;
  }

  @Override
  public boolean isGMLCAddressAVPPresent(){
    return super.message.getAvps().getAvp(Avp.GMLC_ADDRESS) != null;
  }

  @Override
  public java.net.InetAddress getGMLCAddress(){
    Avp lcsGMLCAddressAvp = super.message.getAvps().getAvp(Avp.GMLC_ADDRESS);
    if (lcsGMLCAddressAvp != null) {
      try {
        return lcsGMLCAddressAvp.getAddress();
      } catch (AvpDataException e) {
        logger.debug("Failure trying to obtain GMLC Address AVP value", e);
      }
    }
    return null;
  }

  @Override
  public boolean isPLRFlagsAVPPresent(){
    return super.message.getAvps().getAvp(Avp.PLR_FLAGS) != null;
  }

  @Override
  public long getPLRFLags(){
    Avp lcsPLRFlagsAvp = super.message.getAvps().getAvp(Avp.PLR_FLAGS);
    if (lcsPLRFlagsAvp != null) {
      try {
        return lcsPLRFlagsAvp.getUnsigned32();
      } catch (AvpDataException e) {
        logger.debug("Failure trying to obtain LCS PLR Flags AVP value", e);
      }
    }
    return -1;
  }

  @Override
  public boolean isPeriodicLDRInfoAVPPresent(){
    return super.message.getAvps().getAvp(Avp.PERIODIC_LDR_INFORMATION) != null;
  }

  @Override
  public boolean isReportingAmountAVPPresent(){
    Avp lcsPeriodicLDRInfo = super.message.getAvps().getAvp(Avp.PERIODIC_LDR_INFORMATION);
    if (lcsPeriodicLDRInfo != null) {
      try {
        return lcsPeriodicLDRInfo.getGrouped().getAvp(Avp.REPORTING_AMOUNT) != null;
      } catch (AvpDataException e) {
        logger.debug("Failure trying to obtain Reporting-Amount AVP", e);
      }
    }
    return false;
  }

  @Override
  public long getReportingAmount(){
    Avp lcsPeriodicLDRInfo = super.message.getAvps().getAvp(Avp.PERIODIC_LDR_INFORMATION);
    if (lcsPeriodicLDRInfo != null) {
      try {
        Avp lcsReportingAmountAvp = lcsPeriodicLDRInfo.getGrouped().getAvp(Avp.REPORTING_AMOUNT);
        if (lcsReportingAmountAvp != null){
          return lcsReportingAmountAvp.getUnsigned32();
        }
      } catch (AvpDataException e) {
        logger.debug("Failure trying to obtain LCS Reporting amount AVP value", e);
      }
    }
    return -1;
  }

  @Override
  public boolean isReportingIntervalAVPPresent(){
    Avp lcsPeriodicLDRInfo = super.message.getAvps().getAvp(Avp.PERIODIC_LDR_INFORMATION);
    if (lcsPeriodicLDRInfo != null) {
      try {
        return lcsPeriodicLDRInfo.getGrouped().getAvp(Avp.REPORTING_INTERVAL) != null;
      } catch (AvpDataException e) {
        logger.debug("Failure trying to obtain Reporting-Interval AVP", e);
      }
    }
    return false;
  }

  @Override
  public long getReportingInterval(){
    Avp lcsPeriodicLDRInfo = super.message.getAvps().getAvp(Avp.PERIODIC_LDR_INFORMATION);
    if (lcsPeriodicLDRInfo != null) {
      try {
        Avp lcsReportingIntervalAvp = lcsPeriodicLDRInfo.getGrouped().getAvp(Avp.REPORTING_INTERVAL);
        if (lcsReportingIntervalAvp != null){
          return lcsReportingIntervalAvp.getUnsigned32();
        }
      } catch (AvpDataException e) {
        logger.debug("Failure trying to obtain LCS Reporting-Interval AVP value", e);
      }
    }
    return -1;
  }

}
